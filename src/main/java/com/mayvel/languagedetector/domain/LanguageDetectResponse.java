package com.mayvel.languagedetector.domain;

import java.io.Serializable;

/**
 * Response class for Language detection REST service.
 */

public class LanguageDetectResponse extends Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private String language;
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
