package edu.fmi.mChat.server.response;

import java.io.IOException;
import java.io.Writer;

import edu.fmi.mChat.server.ChatServer;
import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.utils.ResponseCode;

public class RegisterResponse extends BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RegisterResponse.class.getSimpleName();

	private String username;

	public RegisterResponse(final boolean isRegistered, final String username) {
		if (isRegistered) {
			responseCode = ResponseCode.OPERATION_UNSUCCESSFUL;
		} else {
			responseCode = ResponseCode.OPERATION_SUCCESSFUL;
		}
		this.username = username;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		if (responseCode == ResponseCode.OPERATION_SUCCESSFUL) {
			stringBuilder.append(responseCode);
			stringBuilder.append(" ok ");
			stringBuilder.append(username);
			stringBuilder.append(" successfully registered\r\n");
		} else if (responseCode == ResponseCode.OPERATION_UNSUCCESSFUL) {
			stringBuilder.append(responseCode);
			stringBuilder.append(" err ");
			stringBuilder.append(username);
			stringBuilder.append(" already taken\r\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RequestType getRequestType() {
		return RequestType.REGISTER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(final ChatServer server, final Writer clientWriter) throws IOException {
		clientWriter.write(toString());
		clientWriter.flush();
	}
}
