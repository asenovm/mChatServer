package edu.fmi.mChat.server.model;

import edu.fmi.mChat.server.enums.RequestType;

public class SendMessageRequest implements MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = SendMessageRequest.class.getSimpleName();

	private final String sender;

	private final String receiver;

	private final String message;

	public SendMessageRequest(final String sender, final String receiver, final String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.SEND_MESSAGE;
	}

}
