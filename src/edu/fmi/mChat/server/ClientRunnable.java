package edu.fmi.mChat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import edu.fmi.mChat.server.model.MetaRequest;

public class ClientRunnable implements Runnable {

	/**
	 * for debugging purposes only
	 */
	// @SuppressWarnings("unused")
	private static final String TAG = ClientRunnable.class.getSimpleName();

	private final Socket clientSocket;

	public ClientRunnable(final Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(clientSocket
					.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream());

			final String inputLine = reader.readLine();

			final RequestParser parser = new RequestParser();
			final MetaRequest metaRequest = parser.parse(inputLine);
			final BaseServerResponse response = ResponseFactory
					.createResponse(metaRequest);
			writer.write(response.toString());
			writer.flush();

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
