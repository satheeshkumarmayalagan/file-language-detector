package com.mayvel.languagedetector.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mayvel.languagedetector.common.exception.ServiceException;

/**
 * The Class FileUtil support utility class for file related operations.
 */
@Service
public class FileUtil {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * Upload file utility function uploads the multi-part file data into the directory.
	 *
	 * @param file the file
	 * @return the string
	 * @throws ServiceException the service exception
	 */
	public static String uploadFile(MultipartFile file) throws ServiceException {

		BufferedOutputStream stream = null;
		String serverFileLoc = null;

		try {

			String fileName = null;
			byte[] bytes = file.getBytes();
			fileName = file.getOriginalFilename();
			// Creating the directory to store file
			String rootPath = System.getProperty("catalina.home");
			File dir = new File(rootPath + File.separator + "tmpFiles");
			if (!dir.exists())
				dir.mkdirs();
			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + System.currentTimeMillis() + "_" + fileName);
			serverFileLoc = serverFile.getAbsolutePath();

			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);

		} catch (Exception e) {

			throw new ServiceException("Could not upload the file!" + e.getMessage());
		
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				// Do nothing
				e.printStackTrace();
			}
		}

		logger.info("Server File Location=" + serverFileLoc);

		return serverFileLoc;

	}
	
	
	public static boolean validateFileType(MultipartFile file) {
		String fileType = file.getContentType();

		return "text/plain".equals(fileType) || "application/msword".equals(fileType)
				|| "application/pdf".equals(fileType)
				|| "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(fileType);
		
	}


	public static void delete(String serverFileLoc) {
		try {
			File file = new File(serverFileLoc);
			file.delete();
		} catch(Exception e) {
			logger.error("Unable to delete the file = " + serverFileLoc);
		} 
	}

}
