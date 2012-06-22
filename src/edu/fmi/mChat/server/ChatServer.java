package edu.fmi.mChat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.fmi.mChat.server.model.ServerProperties;
import edu.fmi.mChat.server.model.User;
import edu.fmi.mChat.server.response.SendMessageResponse;
import edu.fmi.mChat.server.utils.RemoteAddress;

public class ChatServer {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ChatServer.class.getSimpleName();

	private final Map<RemoteAddress, User> registeredUsers;

	private static final Logger logger;

	static {
		PropertyConfigurator.configure("log4j.properties");
		logger = org.apache.log4j.Logger.getLogger(ChatServer.class);

	}

	/* package */ChatServer() {
		registeredUsers = new HashMap<RemoteAddress, User>();
	}

	public synchronized boolean registerUser(final String username, final InetAddress userAddress,
			final int portNumber) {
		for (final User user : registeredUsers.values()) {
			if (user.getUsername().equals(username)) {
				return false;
			}
		}

		final RemoteAddress remoteAddress = new RemoteAddress(userAddress, portNumber);
		registeredUsers.put(remoteAddress, new User(username, remoteAddress));
		return true;
	}

	public boolean sendMessage(final String receiver, SendMessageResponse response) {
		if (receiver.length() == 0) {
			for (final User user : registeredUsers.values()) {
				writeResponseToSocket(response, user);
			}
		} else {
			User receiverUser = findUser(receiver);
			if (receiverUser == null) {
				return false;
			}

			writeResponseToSocket(response, receiverUser);
		}
		return true;
	}

	private User findUser(final String username) {
		for (final User user : registeredUsers.values()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	private void writeResponseToSocket(final SendMessageResponse response, final User receiverUser) {
		try {
			final RemoteAddress remoteAddress = receiverUser.getRemoteAddress();
			final Socket clientSocket = new Socket(remoteAddress.getInetAddress(), remoteAddress
					.getPortNumber());
			final PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
			writer.write(response.toString());
			writer.flush();
		} catch (IOException e) {
			logger.fatal("send message " + e);
		}

	}

	public static void main(String[] args) {
		final ChatServer server = new ChatServer();
		try {
			final ServerProperties serverProperties = new ServerProperties("server.properties");
			final ServerSocket serverSocket = new ServerSocket(serverProperties.getServerPort());
			final ExecutorService executor = Executors.newCachedThreadPool();
			while (true) {
				final Socket clientSocket = serverSocket.accept();
				final Runnable clientRunnable = new ClientRunnable(clientSocket, server);
				executor.execute(clientRunnable);
			}
		} catch (IOException ex) {
			logger.fatal("cant open socket", ex);
		}
	}

	public User getUser(final RemoteAddress address) {
		return registeredUsers.get(address);
	}

	public synchronized boolean closeConnection(final User user) {
		return registeredUsers.remove(user.getRemoteAddress()) != null;
	}

	public synchronized Collection<User> getActiveUsers() {
		return Collections.unmodifiableCollection(registeredUsers.values());
	}

	public synchronized User getUser(final String username) {
		for (final User user : registeredUsers.values()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
}
