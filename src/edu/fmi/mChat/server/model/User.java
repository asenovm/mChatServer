package edu.fmi.mChat.server.model;

import edu.fmi.mChat.server.utils.RemoteAddress;

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

	private final RemoteAddress remoteAddress;

	/**
	 * Creates a new User with the username given
	 * 
	 * @param username
	 *            the username that is behind this user
	 */
	public User(final String username, final RemoteAddress remoteAddress) {
		this.username = username;
		this.remoteAddress = remoteAddress;
	}

	/**
	 * Returns the username that is associated with this <strong>User</strong>
	 * 
	 * @return the username behind this <strong>User</strong>
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the remote address of this user
	 * 
	 * @return the remote address of this user
	 */
	public RemoteAddress getRemoteAddress() {
		return remoteAddress;
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
