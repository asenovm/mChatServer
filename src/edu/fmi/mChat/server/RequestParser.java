package edu.fmi.mChat.server;

import edu.fmi.mChat.server.model.User;
import edu.fmi.mChat.server.request.CloseConnectionRequest;
import edu.fmi.mChat.server.request.ListActiveUsersRequest;
import edu.fmi.mChat.server.request.MetaRequest;
import edu.fmi.mChat.server.request.RegisterRequest;
import edu.fmi.mChat.server.request.SendFileRequest;
import edu.fmi.mChat.server.request.SendMessageRequest;

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

	private static final String REQUEST_LIST = "list";

	private static final String REQUEST_SEND_FILE = "send_file_to";

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
		} else if (REQUEST_LIST.equals(parsedRequest[0])) {
			return new ListActiveUsersRequest();
		} else if (REQUEST_SEND_FILE.equals(parsedRequest[0])) {
			System.out.println("file path is "
					+ request.substring(request.indexOf(parsedRequest[1])
							+ parsedRequest[1].length() + 1, request.indexOf("port") - 1));
			return new SendFileRequest(parsedRequest[1], request.substring(request
					.indexOf(parsedRequest[1])
					+ parsedRequest[1].length() + 1, request.indexOf("port") - 1), portNumber);
		}

		return null;
	}
}
