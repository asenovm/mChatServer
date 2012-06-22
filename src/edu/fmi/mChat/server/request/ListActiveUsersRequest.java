package edu.fmi.mChat.server.request;

import edu.fmi.mChat.server.enums.RequestType;

public class ListActiveUsersRequest implements MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ListActiveUsersRequest.class.getSimpleName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType getRequestType() {
		return RequestType.LIST_ACTIVE_USERS;
	}

}
