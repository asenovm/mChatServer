package edu.fmi.mChat.server;

import edu.fmi.mChat.server.model.MetaRequest;
import edu.fmi.mChat.server.model.RegisterRequest;

public class ResponseFactory {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ResponseFactory.class.getSimpleName();

	public static BaseServerResponse createResponse(
			final MetaRequest metaRequest) {
		switch (metaRequest.getRequestType()) {
		case REGISTER:
			final RegisterRequest registerRequest = (RegisterRequest) metaRequest;

			final boolean isRegistered = !ChatServer.getInstance()
					.registerUser(registerRequest.getUsername());
			return new RegisterResponse(isRegistered, registerRequest
					.getUsername());
		default:
			return null;
		}
	}

}
