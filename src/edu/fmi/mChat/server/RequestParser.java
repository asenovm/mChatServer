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

		final int portNumber = Integer.parseInt(request.substring(request.lastIndexOf("port")
				+ "port".length() + 1));
		final String[] parsedRequest = request.split(" ");
		final String requestType = parsedRequest[0];

		if (REQUEST_REGISTER.equals(requestType)) {
			return parseRegisterRequest(parsedRequest, portNumber);
		} else if (REQUEST_SEND_MESSAGE.equals(requestType)) {
			return parseSendMessageRequest(requestSender, parsedRequest, request);
		} else if (REQUEST_BYE.equals(requestType)) {
			return parseCloseConnectionRequest(requestSender);
		} else if (REQUEST_LIST.equals(requestType)) {
			return parseListActiveUsersRequest();
		} else if (REQUEST_SEND_FILE.equals(requestType)) {
			return parseSendFileRequest(parsedRequest, request, portNumber);
		}

		return null;
	}

	private MetaRequest parseRegisterRequest(final String[] parsedRequest, final int portNumber) {
		return new RegisterRequest(parsedRequest[1], portNumber);
	}

	private MetaRequest parseSendMessageRequest(final User requestSender,
			final String[] parsedRequest, final String request) {
		return new SendMessageRequest(requestSender.getUsername(), parsedRequest[1], request
				.substring(request.indexOf(parsedRequest[1]) + parsedRequest[1].length() + 1,
						request.lastIndexOf("port")));
	}

	private MetaRequest parseCloseConnectionRequest(final User requestSender) {
		return new CloseConnectionRequest(requestSender);
	}

	private MetaRequest parseListActiveUsersRequest() {
		return new ListActiveUsersRequest();

	}

	private MetaRequest parseSendFileRequest(final String[] parsedRequest, final String request,
			final int portNumber) {
		return new SendFileRequest(parsedRequest[1], request.substring(request
				.indexOf(parsedRequest[1])
				+ parsedRequest[1].length() + 1, request.indexOf("port") - 1), portNumber);
	}
}
