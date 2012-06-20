package edu.fmi.mChat.server.response;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import edu.fmi.mChat.server.ChatServer;
import edu.fmi.mChat.server.enums.RequestType;
import edu.fmi.mChat.server.model.User;

public class ListActiveUsersResponse extends BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ListActiveUsersResponse.class.getSimpleName();

	private final Collection<User> activeUsers;

	public ListActiveUsersResponse(final Collection<User> activeUsers) {
		this.activeUsers = activeUsers;
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("200 ok ");
		for (final User user : activeUsers) {
			stringBuilder.append(user.getUsername());
			stringBuilder.append(" ");
		}
		stringBuilder.append("\r\n");
		return stringBuilder.toString();
	}

	@Override
	protected RequestType getRequestType() {
		return RequestType.LIST_ACTIVE_USERS;
	}

	@Override
	public void send(ChatServer server, Writer clientWriter) throws IOException {
		clientWriter.write(toString());
		clientWriter.flush();
	}
}
