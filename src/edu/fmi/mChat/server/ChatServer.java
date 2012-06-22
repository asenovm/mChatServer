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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

	private final ReadWriteLock lock;

	static {
		PropertyConfigurator.configure("log4j.properties");
		logger = org.apache.log4j.Logger.getLogger(ChatServer.class);

	}

	/* package */ChatServer() {
		registeredUsers = new HashMap<RemoteAddress, User>();
		lock = new ReentrantReadWriteLock();
	}

	public boolean registerUser(final String username, final InetAddress userAddress,
			final int portNumber) {
		if (getUser(username) != null) {
			return false;
		}

		final RemoteAddress remoteAddress = new RemoteAddress(userAddress, portNumber);
		lock.writeLock().lock();
		registeredUsers.put(remoteAddress, new User(username, remoteAddress));
		lock.writeLock().unlock();
		return true;
	}

	public boolean sendMessage(final String receiver, SendMessageResponse response) {
		if (receiver.length() == 0) {
			lock.readLock().lock();
			for (final User user : registeredUsers.values()) {
				writeResponseToSocket(response, user);
			}
			lock.readLock().unlock();
		} else {
			User receiverUser = getUser(receiver);
			if (receiverUser == null) {
				return false;
			}

			writeResponseToSocket(response, receiverUser);
		}
		return true;
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

	public boolean closeConnection(final User user) {
		lock.writeLock().lock();
		final boolean result = registeredUsers.remove(user.getRemoteAddress()) != null;
		lock.writeLock().unlock();
		return result;
	}

	public Collection<User> getActiveUsers() {
		lock.readLock().lock();
		final Collection<User> result = Collections
				.unmodifiableCollection(registeredUsers.values());
		lock.readLock().unlock();
		return result;
	}

	public User getUser(final String username) {
		lock.readLock().lock();
		for (final User user : registeredUsers.values()) {
			if (user.getUsername().equals(username)) {
				lock.readLock().unlock();
				return user;
			}
		}
		lock.readLock().unlock();
		return null;
	}
}
