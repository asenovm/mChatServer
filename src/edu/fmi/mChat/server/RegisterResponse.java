package edu.fmi.mChat.server;

import edu.fmi.mChat.server.utils.ResponseCode;

public class RegisterResponse extends BaseServerResponse {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RegisterResponse.class.getSimpleName();

	private String username;

	public RegisterResponse(final boolean isRegistered, final String username) {
		if (isRegistered) {
			responseCode = ResponseCode.REGISTER_FAIL;
		} else {
			responseCode = ResponseCode.REGISTER_OK;
		}
		this.username = username;
	}

	@Override
	void setSuccessfull(boolean isSuccessfull) {
		// blank
	}

	@Override
	void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		if (responseCode == ResponseCode.REGISTER_OK) {
			stringBuilder.append(responseCode);
			stringBuilder.append(" ok ");
			stringBuilder.append(username);
			stringBuilder.append(" successfully registered\r\n");
		} else if (responseCode == ResponseCode.REGISTER_FAIL) {
			stringBuilder.append(responseCode);
			stringBuilder.append(" err ");
			stringBuilder.append(username);
			stringBuilder.append(" already taken\r\n");
		}
		return stringBuilder.toString();
	}
}
