package com.mayvel.languagedetector.domain.factory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mayvel.languagedetector.domain.LanguageDetectResponse;

/**
 * The ResponseFactory class for all Language detection REST Service.
 */

@Component
public class LanguageDetectResponseFactoryImpl implements ResponseFactory {

	/* (non-Javadoc)
	 * Builds Success Response
	 * @see com.mayvel.languagedetector.domain.factory.ResponseFactory#getSuccessResponse()
	 */
	public LanguageDetectResponse getSuccessResponse() {
		LanguageDetectResponse response = new LanguageDetectResponse();
		response.setStatus("success");
		response.setMessage("language detected successfully!!");
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	/* (non-Javadoc)
	 * Builds Resource Not found Response
	 * @see com.mayvel.languagedetector.domain.factory.ResponseFactory#getNotFoundResponse()
	 */
	public LanguageDetectResponse getNotFoundResponse() {
		LanguageDetectResponse response = new LanguageDetectResponse();
		response.setStatus("error");
		response.setMessage("language cound not be found!!");
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		return response;
	}

	/* (non-Javadoc)
	 * Builds Internal Server Error Response
	 * @see com.mayvel.languagedetector.domain.factory.ResponseFactory#getInternalErrorResponse()
	 */
	public LanguageDetectResponse getInternalErrorResponse() {
		LanguageDetectResponse response = new LanguageDetectResponse();
		response.setStatus("error");
		response.setMessage("internal server error!!");
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}

	/* (non-Javadoc)
	 * @see com.mayvel.languagedetector.domain.factory.ResponseFactory#getBadRequestResponse()
	 */
	public LanguageDetectResponse getBadRequestResponse() {
		LanguageDetectResponse response = new LanguageDetectResponse();
		response.setStatus("error");
		response.setMessage("not a valid request!!");
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		return response;
	}

}
