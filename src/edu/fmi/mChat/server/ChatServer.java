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

/**
 * The main handler of requests. Request are accepted here and handled in
 * separate threads
 * 
 * @author main
 * 
 */
public class ChatServer {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ChatServer.class.getSimpleName();

	private volatile Map<RemoteAddress, User> registeredUsers;

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

	/**
	 * Attempts to register the user using the credentials specified
	 * 
	 * @param username
	 *            the username to be registered
	 * @param userAddress
	 *            the address of the user that would like to be registered
	 * @param portNumber
	 *            the port number at which the user that is trying to be
	 *            registered will listen for asynchronous server repsponses
	 * @return whether or not the registration was successful
	 */
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

	/**
	 * Attepts to send a message using the parameters given
	 * 
	 * @param receiver
	 *            the username of the receiver of the message
	 * @param response
	 *            the response that is to be written to the receiver
	 * @return whether or not the message has been successfully sent to the
	 *         intended receiver
	 */
	public boolean sendMessage(final String sender, final String receiver,
			SendMessageResponse response) {
		if (receiver.length() == 0) {
			lock.readLock().lock();
			for (final User user : registeredUsers.values()) {
				if (!user.getUsername().equals(sender)) {
					writeResponseToSocket(response, user);
				}
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

	/**
	 * Returns the user that has the address specified or {@code null} in case
	 * no such user is registered within the server
	 * 
	 * @param address
	 *            the remote address of the user
	 * @return the user corresponding to the remote address or {@code null} in
	 *         case no user with such address has been found
	 */
	public User getUser(final RemoteAddress address) {
		lock.readLock().lock();
		final User result = registeredUsers.get(address);
		lock.readLock().unlock();
		return result;
	}

	/**
	 * Attempts to unregister the specified user from within the server
	 * 
	 * @param user
	 *            the user that is exiting the server
	 * @return whether or not the atempt to close the connection has been
	 *         successful
	 */
	public boolean closeConnection(final User user) {
		lock.writeLock().lock();
		final boolean result = registeredUsers.remove(user.getRemoteAddress()) != null;
		lock.writeLock().unlock();
		return result;
	}

	/**
	 * Returns unmodifiable collection of all the users registered within the
	 * server
	 * 
	 * @return unmodifiable collection containing all the currently registered
	 *         within the user users
	 */
	public Collection<User> getActiveUsers() {
		lock.readLock().lock();
		final Collection<User> result = Collections
				.unmodifiableCollection(registeredUsers.values());
		lock.readLock().unlock();
		return result;
	}

	/**
	 * Returns the user corresponding to the <strong>username</strong> given in
	 * case such a user is registered within the server or {@code null} in the
	 * other case
	 * 
	 * @param username
	 *            the username of the user to be found
	 * @return the user corresponding to the <strong>username</strong> specified
	 *         in case such a used is registered within the server or null in
	 *         case there's not
	 */
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
