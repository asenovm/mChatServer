package edu.fmi.mChat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ChatServer.class.getSimpleName();

	private static final int PORT_NUMBER = 65535;

	public static void main(String[] args) {
		try {
			final ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
			while (true) {
				final Socket clientSocket = serverSocket.accept();
				final Runnable clientRunnable = new ClientRunnable(clientSocket);
				new Thread(clientRunnable).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
