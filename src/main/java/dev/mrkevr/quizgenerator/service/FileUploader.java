package dev.mrkevr.quizgenerator.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.quizgenerator.exception.InvalidFileFormatException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploader {
	
	
//	private final String UPLOAD_DIR = Paths.get("src/main/resources/files").toAbsolutePath().toString();
	
	@Value("${file.directory}")
	private String UPLOAD_DIR;
	
	public String uploadFile(MultipartFile file, String fileName) {
		
		// File validation
		if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xlsx")) {
			throw new InvalidFileFormatException("Please upload a valid Excel(.xlsx) file.");
		}
		
		try {
			Files.copy(
				file.getInputStream(),
				Paths.get(UPLOAD_DIR + File.separator + fileName + this.getFileExtension(file.getOriginalFilename())),
				StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileName;
	}

	public boolean fileExists(String fileName) {
		boolean isExist = false;
		try {
			File file = new File(UPLOAD_DIR + "/" + fileName + ".xlsx");
			isExist = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}

	/**
	 * Removes all characters before the last 'DOT' from the name.
	 */
	public String getFileExtension(String name) {
		String extension;
		try {
			extension = name.substring(name.lastIndexOf("."));
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}
}