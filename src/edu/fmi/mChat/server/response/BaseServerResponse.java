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

	/**
	 * Returns the type of the request that triggered this response
	 * 
	 * @return the type of the request that triggered this response
	 */
	protected abstract RequestType getRequestType();

	/**
	 * Sends the response to the client
	 * 
	 * @param server
	 *            the server that is to execute this response
	 * @param clientWriter
	 *            the writer to the client socket where the response should be
	 *            written
	 * @throws IOException
	 *             in case the response cannot be send
	 */
	public abstract void send(final ChatServer server, final Writer clientWriter)
			throws IOException;

}
