package com.mayvel.languagedetector.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mayvel.languagedetector.common.exception.ServiceException;
import com.mayvel.languagedetector.common.util.LanguageDetectorUtil;


/**
 * The Class LanguageDetectorService is a service that detects the language in the file by looking into the first line of the file.
 */

@Service
public class LanguageDetectorService {

	@Autowired
	private LanguageDetectorUtil languageDetectorUtil;
	/**
	 * Gets the language from file by reading the first line.
	 *
	 * @param path the path
	 * @return the language from file
	 * @throws ServiceException the service exception
	 */
	
	public String getLanguageFromFile(String path) throws ServiceException {
		String languageCode = languageDetectorUtil.detectLanguage(readFirstLine(path));
		String language = null;
		
		if(!StringUtils.isEmpty(languageCode)) {
			Locale locale = new Locale(languageCode);
			language = locale.getDisplayLanguage();
		}
		
		return language;
	}

	/**
	 * Reads first line of the given file.
	 *
	 * @param path the path
	 * @return the string
	 * @throws ServiceException the service exception
	 */
	static String readFirstLine(String path) throws ServiceException {
		BufferedReader brTest = null;
		String sampleText = null;
		try {
			brTest = new BufferedReader(new FileReader(path));
			// read first line
			sampleText = brTest.readLine();
		} catch (IOException ex) {
			throw new ServiceException("Could not read the first line from the file - " + ex.getMessage());
		} finally {
			try {
				brTest.close();
			} catch (IOException e) {
				// do nothing
			}
		}
		return sampleText;
	}
}
