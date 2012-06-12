package edu.fmi.mChat.server;

import edu.fmi.mChat.server.model.MetaRequest;
import edu.fmi.mChat.server.model.RegisterRequest;

public class RequestParser {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RequestParser.class.getSimpleName();

	/**
	 * {@value}
	 */
	private static final String REQUEST_REGISTER = "user";

	private static final String REQUEST_SEND_MESSAGE = "send_to";

	/**
	 * Returns a meta request associated with the request String specified.
	 * 
	 * @param request
	 *            the request string that has been sent by the client
	 * @return the MetaRequest associated with this request. This is the model
	 *         behind the request
	 * 
	 */
	public MetaRequest parse(final String request) {
		final String[] parsedRequest = request.split(" ");
		if (REQUEST_REGISTER.equals(parsedRequest[0])) {
			return new RegisterRequest(parsedRequest[1]);
		} else if (REQUEST_SEND_MESSAGE.equals(parsedRequest[0])) {
			System.out.println("send message!!!!");
		}
		// as for now
		return null;
	}
}
