package edu.fmi.mChat.server;

import java.net.InetAddress;

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
			final InetAddress requestSource) {
		switch (metaRequest.getRequestType()) {
		case REGISTER:
			return createRegisterResponse((RegisterRequest) metaRequest, requestSource);
		case SEND_MESSAGE:
			return createSendMessageResponse((SendMessageRequest) metaRequest);
		default:
			return null;
		}
	}

	private static BaseServerResponse createRegisterResponse(final RegisterRequest registerRequest,
			final InetAddress requestSource) {
		final boolean isRegistered = !ChatServer.getInstance().registerUser(
				registerRequest.getUsername(), requestSource);
		return new RegisterResponse(isRegistered, registerRequest.getUsername());
	}

	private static BaseServerResponse createSendMessageResponse(final SendMessageRequest request) {
		return new SendMessageResponse(request.getSender(), request.getReceiver(), request
				.getMessage());
	}
}
