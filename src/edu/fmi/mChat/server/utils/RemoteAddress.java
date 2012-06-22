package edu.fmi.mChat.server.utils;

import java.net.InetAddress;

/**
 * Wrapper for the InetAddress and port at wich the remote client is expecting
 * asynchronous messages
 * 
 * @author martin
 * 
 */
public class RemoteAddress {

	/**
	 * debugging purposes only
	 */
	@SuppressWarnings("unused")
	private static final String TAG = RemoteAddress.class.getSimpleName();

	private final InetAddress address;

	private final int portNumber;

	/**
	 * Constructs new RemoteAddress using the parameters given
	 * 
	 * @param address
	 *            the address of the remote client
	 * @param portNumber
	 *            the port at which the remote client is excepting asynchronous
	 *            messages
	 */
	public RemoteAddress(final InetAddress address, final int portNumber) {
		this.address = address;
		this.portNumber = portNumber;
	}

	/**
	 * Returns the address of the remote client
	 * 
	 * @return the address of the remote client
	 */
	public InetAddress getInetAddress() {
		return address;
	}

	/**
	 * Returns the port number at which the client will expect asynchronous
	 * messages
	 * 
	 * @return the port number at which the client will expect asynchronous
	 *         messages
	 */
	public int getPortNumber() {
		return portNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + portNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteAddress other = (RemoteAddress) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (portNumber != other.portNumber)
			return false;
		return true;
	}

}
