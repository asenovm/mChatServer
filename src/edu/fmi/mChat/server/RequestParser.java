package edu.fmi.mChat.server;

import java.util.Arrays;

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
		System.out.println(Arrays.toString(parsedRequest));
		if (REQUEST_REGISTER.equals(parsedRequest[0])) {
			return new RegisterRequest(parsedRequest[1]);
		}
		// as for now
		return null;
	}

}
