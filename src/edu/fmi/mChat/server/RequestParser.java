package edu.fmi.mChat.server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.model.User;
import edu.fmi.mChat.server.request.CloseConnectionRequest;
import edu.fmi.mChat.server.request.ListActiveUsersRequest;
import edu.fmi.mChat.server.request.MetaRequest;
import edu.fmi.mChat.server.request.RegisterRequest;
import edu.fmi.mChat.server.request.SendFileRequest;
import edu.fmi.mChat.server.request.SendMessageRequest;

/**
 * A parser for the requests that are sent by the clients
 * 
 * @author martin
 * 
 */
public class RequestParser {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RequestParser.class.getSimpleName();

	private static final Logger logger;

	static {
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(RequestParser.class);
	}

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

		if (RequestType.REGISTER.toString().equals(requestType)) {
			return parseRegisterRequest(parsedRequest, portNumber);
		} else if (RequestType.SEND_MESSAGE.toString().equals(requestType)) {
			return parseSendMessageRequest(requestSender, parsedRequest, request);
		} else if (RequestType.CLOSE_CONNECTION.toString().equals(requestType)) {
			return parseCloseConnectionRequest(requestSender);
		} else if (RequestType.LIST_ACTIVE_USERS.toString().equals(requestType)) {
			return parseListActiveUsersRequest();
		} else if (RequestType.SEND_FILE.toString().equals(requestType)) {
			return parseSendFileRequest(parsedRequest, request, portNumber);
		}

		return null;
	}

	private MetaRequest parseRegisterRequest(final String[] parsedRequest, final int portNumber) {
		logger.info("new register request");
		return new RegisterRequest(parsedRequest[1], portNumber);
	}

	private MetaRequest parseSendMessageRequest(final User requestSender,
			final String[] parsedRequest, final String request) {
		logger.info("new send message request");
		return new SendMessageRequest(requestSender.getUsername(), parsedRequest[1], request
				.substring(request.indexOf(parsedRequest[1]) + parsedRequest[1].length() + 1,
						request.lastIndexOf("port")));
	}

	private MetaRequest parseCloseConnectionRequest(final User requestSender) {
		logger.info("new close connection request");
		return new CloseConnectionRequest(requestSender);
	}

	private MetaRequest parseListActiveUsersRequest() {
		logger.info("new list active users request");
		return new ListActiveUsersRequest();

	}

	private MetaRequest parseSendFileRequest(final String[] parsedRequest, final String request,
			final int portNumber) {
		logger.info("new send file request");
		return new SendFileRequest(parsedRequest[1], request.substring(request
				.indexOf(parsedRequest[1])
				+ parsedRequest[1].length() + 1, request.indexOf("port") - 1), portNumber);
	}
}
