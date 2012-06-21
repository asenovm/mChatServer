package edu.fmi.mChat.server.request;

import edu.fmi.mChat.server.enums.RequestType;

public class SendFileRequest implements MetaRequest {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = SendFileRequest.class.getSimpleName();

	private final String receiver;

	private final String filePath;

	private final int portNumber;

	public SendFileRequest(final String receiver, final String filePath, final int listeningPort) {
		this.receiver = receiver;
		this.filePath = filePath;
		portNumber = listeningPort;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.SEND_FILE;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getFilePath() {
		return filePath;
	}

	public int getPortNumber() {
		return portNumber;
	}

}
