package edu.fmi.mChat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import edu.fmi.mChat.server.model.MetaRequest;
import edu.fmi.mChat.server.model.User;
import edu.fmi.mChat.server.utils.RemoteAddress;

public class ClientRunnable implements Runnable {

	/**
	 * for debugging purposes only
	 */
	// @SuppressWarnings("unused")
	private static final String TAG = ClientRunnable.class.getSimpleName();

	private final Socket clientSocket;

	private final ChatServer server;

	public ClientRunnable(final Socket clientSocket, final ChatServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
	}

	@Override
	public void run() {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream());

			final RequestParser parser = new RequestParser();

			final String request = reader.readLine();
			final int portNumber = Integer.parseInt(request.substring(request.lastIndexOf(" ") + 1,
					request.length()));

			final RemoteAddress address = new RemoteAddress(clientSocket.getInetAddress(),
					portNumber);

			final User requestSender = server.getUser(address);

			final MetaRequest metaRequest = parser.parse(requestSender, request);

			final BaseServerResponse response = ResponseFactory.createResponse(metaRequest,
					clientSocket.getInetAddress(), server);

			response.send(server, writer);

		} catch (IOException ex) {
			Logger.getAnonymousLogger().throwing(TAG, "run", ex);
		} finally {
			writer.close();
			try {
				reader.close();
			} catch (IOException e) {
				Logger.getAnonymousLogger().throwing(TAG, "run", e);
			}
		}
	}
}
