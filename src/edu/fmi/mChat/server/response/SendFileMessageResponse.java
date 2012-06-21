package edu.fmi.mChat.server.response;

import java.io.IOException;
import java.io.Writer;

import edu.fmi.mChat.server.ChatServer;
import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.model.User;
import edu.fmi.mChat.server.utils.RemoteAddress;

public class SendFileMessageResponse extends BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = SendFileMessageResponse.class.getSimpleName();

	private final User receiver;

	private final String filePath;

	public SendFileMessageResponse(final User receiver, final String filePath) {
		this.receiver = receiver;
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		if (receiver != null) {
			final RemoteAddress receiverAddress = receiver.getRemoteAddress();
			builder.append("200 ok ");
			builder.append(receiverAddress.getInetAddress().getHostAddress());
			builder.append(" ");
			builder.append(receiverAddress.getPortNumber());
			builder.append(" ");
			builder.append(filePath);
		} else {
			builder.append("100 error");
		}

		return builder.toString();
	}

	@Override
	protected RequestType getRequestType() {
		return RequestType.SEND_FILE;
	}

	@Override
	public void send(ChatServer server, Writer clientWriter) throws IOException {
		clientWriter.write(toString());
		clientWriter.flush();
	}

}
