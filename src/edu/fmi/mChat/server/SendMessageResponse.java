package edu.fmi.mChat.server;

import java.io.IOException;
import java.io.Writer;

import edu.fmi.mChat.server.enums.RequestType;

public class SendMessageResponse extends BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = SendMessageResponse.class.getSimpleName();

	private final String sender;

	private final String receiver;

	private final String message;

	public SendMessageResponse(final String sender, final String receiver, final String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("300 msg_from ");
		builder.append(sender);
		builder.append(" ");
		builder.append(message);
		builder.append("\r\n");
		return builder.toString();
	}

	@Override
	protected RequestType getRequestType() {
		return RequestType.SEND_MESSAGE;
	}

	@Override
	protected void send(Writer clientWriter) throws IOException {
		ChatServer.getInstance().sendMessage(receiver, this);
	}
}
