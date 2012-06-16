package edu.fmi.mChat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import edu.fmi.mChat.server.model.User;

public class ChatServer {

	/**
	 * for debugging purposes only
	 */
	// @SuppressWarnings("unused")
	private static final String TAG = ChatServer.class.getSimpleName();

	private static final int PORT_NUMBER_INCOMING = 65535;

	private static final int PORT_NUMBER_OUTCOMING = 65534;

	private static ChatServer serverInstance;

	private final Map<InetAddress, User> registeredUsers;

	private ChatServer() {
		registeredUsers = new HashMap<InetAddress, User>();
	}

	public static synchronized ChatServer getInstance() {
		if (serverInstance == null) {
			serverInstance = new ChatServer();
		}
		return serverInstance;
	}

	public synchronized boolean registerUser(final String username, final InetAddress userAddress) {
		for (final User user : registeredUsers.values()) {
			if (user.getUsername().equals(username)) {
				return false;
			}
		}

		registeredUsers.put(userAddress, new User(username, userAddress));
		return true;
	}

	public boolean sendMessage(final String receiver, SendMessageResponse response) {
		User receiverUser = null;
		for (final User user : registeredUsers.values()) {
			if (user.getUsername().equals(receiver)) {
				receiverUser = user;
				break;
			}
		}

		if (receiverUser == null) {
			return false;
		} else {
			try {
				final Socket clientSocket = new Socket(receiverUser.getAddress(),
						PORT_NUMBER_OUTCOMING);
				final PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				writer.write(response.toString());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				Logger.getAnonymousLogger().throwing(TAG, "sendMessage", e);
			}
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			final ServerSocket serverSocket = new ServerSocket(PORT_NUMBER_INCOMING);
			final ExecutorService executor = Executors.newCachedThreadPool();
			while (true) {
				final Socket clientSocket = serverSocket.accept();
				final Runnable clientRunnable = new ClientRunnable(clientSocket, ChatServer
						.getInstance().registeredUsers.get(clientSocket.getInetAddress()));
				executor.execute(clientRunnable);
			}
		} catch (IOException ex) {
			Logger.getAnonymousLogger().throwing(TAG, "main", ex);
		}
	}
}
