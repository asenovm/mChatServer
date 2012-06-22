package edu.fmi.mChat.server.enums;

public enum RequestType {
	REGISTER("user"), SEND_MESSAGE("send_to"), CLOSE_CONNECTION("bye"), LIST_ACTIVE_USERS("list"), SEND_FILE(
			"send_file_to");

	private final String requestType;

	private RequestType(final String requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return requestType;
	}
}
