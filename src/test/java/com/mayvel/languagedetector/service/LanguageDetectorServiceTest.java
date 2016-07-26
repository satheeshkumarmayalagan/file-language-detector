package com.mayvel.languagedetector.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mayvel.languagedetector.common.exception.ServiceException;

/**
 * The Class LanguageDetectorServiceTest runs the test cases for the REST Service for file detection REST Service /detectLanguage
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
@WebAppConfiguration


public class LanguageDetectorServiceTest {

	/** The multipart english txt. */
	MockMultipartFile mEnglishTxt = null;
	
	/** The multipart french txt. */
	MockMultipartFile mFrenchTxt = null;
	
	/** The multipart english pdf. */
	MockMultipartFile mEnglishPdf = null;
	
	/** The multipart no file. */
	MockMultipartFile mNoFile = null;
	
	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The language detector service. */
	@Autowired
	LanguageDetectorService languageDetectorService;
	
	/** The context. */
	@Autowired
	private WebApplicationContext context;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		//Setup English
		mEnglishTxt = getMockMultipartFile("english.txt");
		//Setup French
		mFrenchTxt = getMockMultipartFile("french.txt");
		//Setup English PDF
		mEnglishPdf = getMockMultipartFile("english.pdf");
		mNoFile = getMockMultipartFile("noFile.txt");
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	/* Test case to check if the english is detected */
	
	/**
	 * Test english txt detection.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testEnglishTxtDetection() throws Exception {
		try {
			
			mockMvc.perform(fileUpload("/detectLanguage").file(mEnglishTxt))
			    .andExpect(status().isOk())
                .andExpect(jsonPath("$.language").value("English"));
		                
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	/* Test case to check if the english is detected */

	/**
	 * Test french txt detection.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testFrenchTxtDetection() throws Exception {
		try {
			
			mockMvc.perform(fileUpload("/detectLanguage").file(mFrenchTxt))
			    .andExpect(status().isOk())
                .andExpect(jsonPath("$.language").value("French"));
		                
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/* Test case to check if the english pdf is detected */

	/**
	 * Test english pdf detection.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testEnglishPdfDetection() throws Exception {
		try {
			
			mockMvc.perform(fileUpload("/detectLanguage").file(mEnglishPdf))
			    .andExpect(status().isOk())
                .andExpect(jsonPath("$.language").value("English"));
		                
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}	

	/* Test case to check if error is returned if no file is uploaded */

	/**
	 * Test no file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testNoFile() throws Exception {
		try {
			mockMvc.perform(fileUpload("/detectLanguage").file(mNoFile))
			    .andExpect(status().isBadRequest());
		                
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Gets the mock multipart file.
	 *
	 * @param fileName the file name
	 * @return the mock multipart file
	 */
	private MockMultipartFile getMockMultipartFile(String fileName) {
		Path path = Paths.get("src/test/resources/requests/" + fileName);
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		//MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		return new MockMultipartFile("file", "orig", null, content);
	}

}
