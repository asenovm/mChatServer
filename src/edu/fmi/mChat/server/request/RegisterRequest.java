package edu.fmi.mChat.server.request;

import edu.fmi.mChat.server.enums.RequestType;

/**
 * Holder for the meta information about the user register request sent from the
 * client
 * 
 * @author martin
 * 
 */
public class RegisterRequest implements MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RegisterRequest.class.getSimpleName();

	private final String username;

	private final int portNumber;

	/**
	 * Creates new MetaRequest
	 * 
	 * @param username
	 *            the username that is to be registered within the server
	 */
	public RegisterRequest(final String username, final int portNumber) {
		this.username = username;
		this.portNumber = portNumber;
	}

	/**
	 * Returns the username that is to be registered within the server as per
	 * this request.
	 * 
	 * @return the username that is to registered within the server as per this
	 *         request
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType getRequestType() {
		return RequestType.REGISTER;
	}

	public int getPortNumber() {
		return portNumber;
	}

}
