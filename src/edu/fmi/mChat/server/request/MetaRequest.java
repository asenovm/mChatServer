package edu.fmi.mChat.server.request;

import edu.fmi.mChat.server.enums.RequestType;

/**
 * Implementations of this interface are model classes, holding the information
 * about the client request that has been sent
 * 
 * @author martin
 * 
 */
public interface MetaRequest {
	/**
	 * 
	 * @return the request type of this request
	 */
	RequestType getRequestType();
}
