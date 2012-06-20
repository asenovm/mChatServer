package edu.fmi.mChat.server;

import java.net.InetAddress;

import edu.fmi.mChat.server.model.CloseConnectionRequest;
import edu.fmi.mChat.server.model.MetaRequest;
import edu.fmi.mChat.server.model.RegisterRequest;
import edu.fmi.mChat.server.model.SendMessageRequest;

public class ResponseFactory {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ResponseFactory.class.getSimpleName();

	public static BaseServerResponse createResponse(final MetaRequest metaRequest,
			final InetAddress requestSource, final ChatServer server) {
		switch (metaRequest.getRequestType()) {
		case REGISTER:
			return createRegisterResponse((RegisterRequest) metaRequest, requestSource, server);
		case SEND_MESSAGE:
			return createSendMessageResponse((SendMessageRequest) metaRequest);
		case CLOSE_CONNECTION:
			return createByeResponse((CloseConnectionRequest) metaRequest, server);
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
}
