package edu.fmi.mChat.server.utils;

public class ResponseCode {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ResponseCode.class.getSimpleName();

	private ResponseCode() {
		// blank
	}

	/**
	 * {@value}
	 */
	public static final int OPERATION_SUCCESSFUL = 200;

	/**
	 * {@value}
	 */
	public static final int OPERATION_UNSUCCESSFUL = 100;

}
