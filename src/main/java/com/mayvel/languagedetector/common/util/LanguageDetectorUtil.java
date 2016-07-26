package com.mayvel.languagedetector.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;
import com.mayvel.languagedetector.common.exception.ServiceException;


/**
 * The Class LanguageDetectorUtil uses the detectlanguage API to find the language of the given text.
 */

@Component
public class LanguageDetectorUtil {

	/**
	 * Detect language.
	 *
	 * @param text the text
	 * @return the string
	 * @throws ServiceException the service exception
	 */
	@Value("${language.detection.api.key}")
	private String apiKey;
	
	public String detectLanguage(String text) throws ServiceException {
		
		DetectLanguage.apiKey = apiKey;
		try {
			return DetectLanguage.simpleDetect(text);
		} catch (APIError e) {
			throw new ServiceException("Could not find the language of the given text - " + e.getMessage());
		}
	}
	
}
