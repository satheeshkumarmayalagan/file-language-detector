package com.mayvel.languagedetector.domain.factory;

import com.mayvel.languagedetector.domain.Response;


/**
 * A factory for creating Response objects.
 */

public interface ResponseFactory {
	
	/**
	 * Gets the success response.
	 *
	 * @return the success response
	 */
	Response getSuccessResponse();
	
	/**
	 * Gets the not found response.
	 *
	 * @return the not found response
	 */
	Response getNotFoundResponse();
	
	/**
	 * Gets the internal error response.
	 *
	 * @return the internal error response
	 */
	Response getInternalErrorResponse() ;
	
	/**
	 * Gets the bad request response.
	 *
	 * @return the bad request response
	 */
	Response getBadRequestResponse();

}
