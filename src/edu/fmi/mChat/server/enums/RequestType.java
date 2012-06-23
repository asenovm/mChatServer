package edu.fmi.mChat.server.enums;

/**
 * An enumeration specifying the type of the request that is being handled
 * 
 * @author martin
 * 
 */
public enum RequestType {
	/**
	 * {@value}
	 */
	REGISTER("user"),
	/**
	 * {@value}
	 */
	SEND_MESSAGE("send_to"),
	/**
	 * {@value}
	 */
	SEND_MESSAGE_TO_ALL("send_all"),
	/**
	 * {@value}
	 */
	CLOSE_CONNECTION("bye"),
	/**
	 * {@value}
	 */
	LIST_ACTIVE_USERS("list"),
	/**
	 * {@value}
	 */
	SEND_FILE("send_file_to");

	private final String requestType;

	private RequestType(final String requestType) {
		this.requestType = requestType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return requestType;
	}
}
