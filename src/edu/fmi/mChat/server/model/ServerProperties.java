package edu.fmi.mChat.server.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * A value object holding the properties related to the configuration of the
 * server (in terms of port used).
 * 
 * @author martin
 * 
 */
public class ServerProperties {

	/**
	 * for debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = ServerProperties.class.getSimpleName();

	/**
	 * {@value}
	 */
	private static final String KEY_SERVER_ADDRESS = "server.address";

	/**
	 * {@value}
	 */
	private static final String KEY_SERVER_PORT = "server.port";

	private static final Logger logger;

	private final Map<String, String> properties;

	static {
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(ServerProperties.class);
	}

	/**
	 * Constructs new server properties using the file specified for reading the
	 * respective values
	 * 
	 * @param propertiesFile
	 */
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

	/**
	 * Returns the address at which this server will be running
	 * 
	 * @return the address at which this server is running
	 */
	public String getAddress() {
		return properties.get(KEY_SERVER_ADDRESS);
	}

	/**
	 * Returns the port number at which this server will receive requests
	 * 
	 * @return the port number at which this server receives requests
	 */
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
