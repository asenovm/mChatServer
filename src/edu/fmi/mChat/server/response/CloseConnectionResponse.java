package edu.fmi.mChat.server.response;

import java.io.IOException;
import java.io.Writer;

import edu.fmi.mChat.server.ChatServer;
import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.utils.ResponseCode;

public class CloseConnectionResponse extends BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = CloseConnectionResponse.class.getSimpleName();

	public CloseConnectionResponse(final boolean isOperationSuccessfull) {
		if (isOperationSuccessfull) {
			responseCode = ResponseCode.OPERATION_SUCCESSFUL;
		} else {
			responseCode = ResponseCode.OPERATION_UNSUCCESSFUL;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		if (responseCode == ResponseCode.OPERATION_SUCCESSFUL) {
			stringBuilder.append(ResponseCode.OPERATION_SUCCESSFUL);
			stringBuilder.append(" ");
			stringBuilder.append("bye successful");
		} else {
			stringBuilder.append(ResponseCode.OPERATION_UNSUCCESSFUL);
			stringBuilder.append(" ");
			stringBuilder.append("bye unsuccessful");
		}
		return stringBuilder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RequestType getRequestType() {
		return RequestType.CLOSE_CONNECTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(ChatServer server, Writer clientWriter) throws IOException {
		clientWriter.write(toString());
		clientWriter.flush();
	}

}
