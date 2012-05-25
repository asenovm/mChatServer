package edu.fmi.mChat.server.model;

import edu.fmi.mChat.server.enums.RequestType;

public class MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = MetaRequest.class.getSimpleName();

	private final RequestType requestType;

	private final String[] parameters;

	public MetaRequest(final RequestType type, final String... parameters) {
		requestType = type;
		this.parameters = parameters;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public String[] getParameters() {
		return parameters;
	}

}
