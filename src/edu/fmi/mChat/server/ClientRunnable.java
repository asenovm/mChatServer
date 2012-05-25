package edu.fmi.mChat.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientRunnable implements Runnable {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ClientRunnable.class.getSimpleName();

	private final Socket clientSocket;

	public ClientRunnable(final Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		Scanner scanner;
		try {
			scanner = new Scanner(clientSocket.getInputStream());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
		} catch (IOException e) {
			Logger.getAnonymousLogger().throwing(TAG, "run", e);
		}
	}
}
