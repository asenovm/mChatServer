package edu.fmi.mChat.server.model;

import java.net.InetAddress;

/**
 * 
 * Model class for users that are registered within the server
 * 
 * @author martin
 * 
 */
public class User {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = User.class.getSimpleName();

	private final String username;

	private final InetAddress userAddress;

	private final int listeningPortNumber;

	/**
	 * Creates a new User with the username given
	 * 
	 * @param username
	 *            the username that is behind this user
	 */
	public User(final String username, final InetAddress address, final int listeningPortNumber) {
		this.username = username;
		this.userAddress = address;
		this.listeningPortNumber = listeningPortNumber;
	}

	/**
	 * Returns the username that is associated with this <strong>User</strong>
	 * 
	 * @return the username behind this <strong>User</strong>
	 */
	public String getUsername() {
		return username;
	}

	public InetAddress getAddress() {
		return userAddress;
	}

	public int getListeningPortNumber() {
		return listeningPortNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
