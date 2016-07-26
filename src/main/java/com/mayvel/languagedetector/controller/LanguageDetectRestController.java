package com.mayvel.languagedetector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mayvel.languagedetector.domain.LanguageDetectResponse;
import com.mayvel.languagedetector.domain.Response;
import com.mayvel.languagedetector.domain.factory.LanguageDetectResponseFactoryImpl;
import com.mayvel.languagedetector.service.LanguageDetectorService;
import com.mayvel.languagedetector.common.util.FileUtil;

/**
 * The Class LanguageDetectRestController is a REST service which accepts file
 * as multi-part data for language detection. The file is scanned for the
 * language and if found is returned back in the response
 * 
 */

@Controller
public class LanguageDetectRestController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(LanguageDetectRestController.class);

	/** The language detector service. */
	@Autowired
	LanguageDetectorService languageDetectorService;

	@Autowired
	LanguageDetectResponseFactoryImpl responseFactory;

	@Autowired
	FileUtil fileUtil;

	/**
	 * /detectLanguage REST Service accepts file as multi-part data for
	 * language detection The file is scanned for the language and if found is
	 * returned back in the response
	 *
	 * @param file
	 *            the file
	 * @return the response entity
	 */
	@RequestMapping(value = "/detectLanguage", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<Response> uploadFileHandler(@RequestParam("file") MultipartFile file) {

		LanguageDetectResponse response = null;
		String serverFileLoc = null;
		
		if (!file.isEmpty()) {

			logger.info("Rerquest received for file = " + file.getOriginalFilename());

			try {
				serverFileLoc = FileUtil.uploadFile(file);
				String language = languageDetectorService.getLanguageFromFile(serverFileLoc);
				response = StringUtils.isEmpty(language) ? responseFactory.getNotFoundResponse()
						: responseFactory.getSuccessResponse();
				response.setLanguage(language);
			} catch (Exception e) {
				response = responseFactory.getInternalErrorResponse();
			} finally {
				if(!StringUtils.isEmpty(serverFileLoc)) {
					FileUtil.delete(serverFileLoc);
				}
			}

		} else {
			response = responseFactory.getBadRequestResponse();
		}

		logger.info("Response = " + response);

		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}

	
}
