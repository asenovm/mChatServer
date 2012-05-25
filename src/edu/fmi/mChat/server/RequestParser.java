package edu.fmi.mChat.server;

import java.util.Arrays;

import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.model.MetaRequest;

public class RequestParser {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RequestParser.class.getSimpleName();

	private static final String REQUEST_REGISTER = "user";

	public MetaRequest parse(final String request) {
		final String[] parsedRequest = request.split(" ");
		System.out.println(Arrays.toString(parsedRequest));
		if (REQUEST_REGISTER.equals(parsedRequest[0])) {
			return new MetaRequest(RequestType.REGISTER, parsedRequest);
		}
		// as for now
		return null;
	}

}
