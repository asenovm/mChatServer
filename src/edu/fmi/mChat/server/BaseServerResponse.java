package edu.fmi.mChat.server;

public abstract class BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = BaseServerResponse.class.getSimpleName();

	protected int responseCode;

	protected String responseMessage;

	abstract void setSuccessfull(final boolean isSuccessfull);

	abstract void setUsername(final String username);

}
