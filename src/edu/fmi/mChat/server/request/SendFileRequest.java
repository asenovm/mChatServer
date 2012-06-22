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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType getRequestType() {
		return RequestType.SEND_FILE;
	}

	/**
	 * Returns the receiver of the file that is handled in the request
	 * 
	 * @return the receiver of the file that is handled in the request
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Return the path on the file system of the sender of this request to the
	 * file that is to be sent
	 * 
	 * @return the path on the file system of the sender of this request to the
	 *         file that is to be sent
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Returns the port number at which the client will be listening for
	 * responses
	 * 
	 * @return the port number at which the client will be listening for
	 *         responses
	 */
	public int getPortNumber() {
		return portNumber;
	}

}
