package edu.fmi.mChat.server.response;

import java.io.IOException;
import java.io.Writer;

import edu.fmi.mChat.server.ChatServer;
import edu.fmi.mChat.server.enums.RequestType;

public abstract class BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = BaseServerResponse.class.getSimpleName();

	protected int responseCode;

	protected String responseMessage;

	protected abstract RequestType getRequestType();

	public abstract void send(final ChatServer server, final Writer clientWriter)
			throws IOException;

}
