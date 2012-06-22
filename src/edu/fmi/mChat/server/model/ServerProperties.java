package edu.fmi.mChat.server.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ServerProperties {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ServerProperties.class.getSimpleName();

	private static final String KEY_SERVER_ADDRESS = "server.address";

	private static final String KEY_SERVER_PORT = "server.port";

	private static final Logger logger;

	private final Map<String, String> properties;

	static {
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(ServerProperties.class);
	}

	public ServerProperties(final String propertiesFile) {
		properties = new HashMap<String, String>();
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				final String[] tokens = currentLine.split("=");
				properties.put(tokens[0], tokens[1]);
			}
		} catch (FileNotFoundException ex) {
			logger.fatal("cant find properties file", ex);
		} catch (IOException ex) {
			logger.fatal("cant read properties file", ex);
		}
	}

	public String getAddress() {
		return properties.get(KEY_SERVER_ADDRESS);
	}

	public int getServerPort() {
		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(properties.get(KEY_SERVER_PORT));
		} catch (NumberFormatException ex) {
			logger.fatal("cant read port number", ex);
		}
		return portNumber;
	}
}
