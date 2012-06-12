package edu.fmi.mChat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import edu.fmi.mChat.server.model.User;

public class ChatServer {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ChatServer.class.getSimpleName();

	private static final int PORT_NUMBER = 65535;

	private static ChatServer serverInstance;

	private final Set<User> registeredUsers;

	private ChatServer() {
		registeredUsers = new HashSet<User>();
	}

	public static synchronized ChatServer getInstance() {
		if (serverInstance == null) {
			serverInstance = new ChatServer();
		}
		return serverInstance;
	}

	public synchronized boolean registerUser(final String username) {
		System.out.println("new user " + username);
		return registeredUsers.add(new User(username));
	}

	public static void main(String[] args) {
		try {
			final ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
			while (true) {
				final Socket clientSocket = serverSocket.accept();
				final Runnable clientRunnable = new ClientRunnable(clientSocket);
				new Thread(clientRunnable).start();
			}
		} catch (IOException ex) {
			Logger.getAnonymousLogger().throwing(TAG, "main", ex);
		}
	}

}
