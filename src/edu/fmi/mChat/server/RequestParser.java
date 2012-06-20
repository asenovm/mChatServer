package edu.fmi.mChat.server;

import edu.fmi.mChat.server.model.MetaRequest;
import edu.fmi.mChat.server.model.RegisterRequest;
import edu.fmi.mChat.server.model.SendMessageRequest;
import edu.fmi.mChat.server.model.User;

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
	public MetaRequest parse(final User requestSender, final String request) {
		System.out.println("in the server " + request);
		final String[] parsedRequest = request.split(" ");
		if (REQUEST_REGISTER.equals(parsedRequest[0])) {
			return new RegisterRequest(parsedRequest[1], Integer.parseInt(parsedRequest[3]));
		} else if (REQUEST_SEND_MESSAGE.equals(parsedRequest[0])) {
			System.out.println("message is "
					+ request.substring(request.indexOf(parsedRequest[1])
							+ parsedRequest[1].length() + 1, request.lastIndexOf("port")));
			return new SendMessageRequest(requestSender.getUsername(), parsedRequest[1], request
					.substring(request.indexOf(parsedRequest[1]) + parsedRequest[1].length() + 1,
							request.lastIndexOf("port")));
		}
		// as for now
		return null;
	}
}
