package edu.fmi.mChat.server.model;

import edu.fmi.mChat.server.enums.RequestType;

public class CloseConnectionRequest implements MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = CloseConnectionRequest.class.getSimpleName();

	private final User requestSender;

	public CloseConnectionRequest(final User requestSender) {
		this.requestSender = requestSender;
	}

	public User getRequestSender() {
		return requestSender;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.CLOSE_CONNECTION;
	}

}
