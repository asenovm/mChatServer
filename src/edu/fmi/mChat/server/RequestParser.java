package edu.fmi.mChat.server;

import edu.fmi.mChat.server.model.CloseConnectionRequest;
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

	private static final String REQUEST_BYE = "bye";

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
		final int portNumber = Integer.parseInt(request.substring(request.lastIndexOf("port")
				+ "port".length() + 1));
		final String[] parsedRequest = request.split(" ");
		if (REQUEST_REGISTER.equals(parsedRequest[0])) {
			return new RegisterRequest(parsedRequest[1], portNumber);
		} else if (REQUEST_SEND_MESSAGE.equals(parsedRequest[0])) {
			return new SendMessageRequest(requestSender.getUsername(), parsedRequest[1], request
					.substring(request.indexOf(parsedRequest[1]) + parsedRequest[1].length() + 1,
							request.lastIndexOf("port")));
		} else if (REQUEST_BYE.equals(parsedRequest[0])) {
			return new CloseConnectionRequest(requestSender);
		}
		return null;
	}
}
