package edu.fmi.mChat.server;

import java.net.InetAddress;
import java.util.Collection;

import edu.fmi.mChat.server.model.User;
import edu.fmi.mChat.server.request.CloseConnectionRequest;
import edu.fmi.mChat.server.request.ListActiveUsersRequest;
import edu.fmi.mChat.server.request.MetaRequest;
import edu.fmi.mChat.server.request.RegisterRequest;
import edu.fmi.mChat.server.request.SendFileRequest;
import edu.fmi.mChat.server.request.SendMessageRequest;
import edu.fmi.mChat.server.response.BaseServerResponse;
import edu.fmi.mChat.server.response.CloseConnectionResponse;
import edu.fmi.mChat.server.response.ListActiveUsersResponse;
import edu.fmi.mChat.server.response.RegisterResponse;
import edu.fmi.mChat.server.response.SendFileMessageResponse;
import edu.fmi.mChat.server.response.SendMessageResponse;

/**
 * A factory used for creating the appropriate response to be sent to the
 * clients
 * 
 * @author martin
 * 
 */
public class ResponseFactory {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ResponseFactory.class.getSimpleName();
	
	private ResponseFactory(){
		//blank
	}

	/**
	 * Creates a new response object
	 * 
	 * @param metaRequest
	 *            the request that has been sent
	 * @param requestSource
	 *            the IP address of the sender of this request
	 * @param server
	 *            the server instance that is to handle the response
	 * @return the response object that is to be sent to the clients
	 */
	public static BaseServerResponse createResponse(final MetaRequest metaRequest,
			final InetAddress requestSource, final ChatServer server) {
		switch (metaRequest.getRequestType()) {
		case REGISTER:
			return createRegisterResponse((RegisterRequest) metaRequest, requestSource, server);
		case SEND_MESSAGE:
		case SEND_MESSAGE_TO_ALL:
			return createSendMessageResponse((SendMessageRequest) metaRequest);
		case CLOSE_CONNECTION:
			return createByeResponse((CloseConnectionRequest) metaRequest, server);
		case LIST_ACTIVE_USERS:
			return createListUsersResponse((ListActiveUsersRequest) metaRequest, server);
		case SEND_FILE:
			return createSendFileMessage((SendFileRequest) metaRequest, server);
		default:
			return null;
		}
	}

	private static BaseServerResponse createRegisterResponse(final RegisterRequest registerRequest,
			final InetAddress requestSource, final ChatServer server) {
		final boolean isRegistered = !server.registerUser(registerRequest.getUsername(),
				requestSource, registerRequest.getPortNumber());
		return new RegisterResponse(isRegistered, registerRequest.getUsername());
	}

	private static BaseServerResponse createSendMessageResponse(final SendMessageRequest request) {
		return new SendMessageResponse(request.getSender(), request.getReceiver(), request
				.getMessage());
	}

	private static BaseServerResponse createByeResponse(final CloseConnectionRequest request,
			final ChatServer server) {
		final boolean result = server.closeConnection(request.getRequestSender());
		return new CloseConnectionResponse(result);
	}

	private static BaseServerResponse createListUsersResponse(final ListActiveUsersRequest request,
			final ChatServer server) {
		final Collection<User> activeUsers = server.getActiveUsers();
		return new ListActiveUsersResponse(activeUsers);
	}

	private static BaseServerResponse createSendFileMessage(final SendFileRequest request,
			final ChatServer server) {
		final User receiver = server.getUser(request.getReceiver());
		return new SendFileMessageResponse(receiver, request.getFilePath());
	}
}
