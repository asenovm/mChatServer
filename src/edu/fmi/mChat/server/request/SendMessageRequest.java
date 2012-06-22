package edu.fmi.mChat.server.request;

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

	/**
	 * Returns the username of the sender of this request
	 * 
	 * @return the username of the sender of this request
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Returns the username of the receiver of this message
	 * 
	 * @return the username of the receiver of this message
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Returns the message that is to be transmitted to the receiver
	 * 
	 * @return the message that is to be transmitted to the receiver
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType getRequestType() {
		return RequestType.SEND_MESSAGE;
	}

}
