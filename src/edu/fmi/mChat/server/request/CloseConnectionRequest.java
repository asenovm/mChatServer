package edu.fmi.mChat.server.request;

import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.model.User;

public class CloseConnectionRequest implements MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = CloseConnectionRequest.class.getSimpleName();

	private final User requestSender;

	/**
	 * Constructs new {@link CloseConnectionRequest} using the parameter
	 * specified
	 * 
	 * @param requestSender
	 *            the user that sent this request
	 */
	public CloseConnectionRequest(final User requestSender) {
		this.requestSender = requestSender;
	}

	/**
	 * Returns the user that sent this request
	 * 
	 * @return the user that sent this request
	 */
	public User getRequestSender() {
		return requestSender;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType getRequestType() {
		return RequestType.CLOSE_CONNECTION;
	}

}
