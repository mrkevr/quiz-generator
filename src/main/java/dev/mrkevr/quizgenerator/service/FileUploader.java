package dev.mrkevr.quizgenerator.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploader {

	private final String UPLOAD_DIR = Paths.get("src/main/resources/files").toAbsolutePath().toString();
	
	public String uploadFile(MultipartFile file, String fileName) {

		try {
			Files.copy(file.getInputStream(),
					Paths.get(UPLOAD_DIR + File.separator + fileName + this.getFileExtension(file.getOriginalFilename())),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileName;
	}

	public boolean fileExists(MultipartFile multipartFile) {
		boolean isExist = false;
		try {
			File file = new File(UPLOAD_DIR + multipartFile.getOriginalFilename());
			isExist = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}

	/**
	 * Removes all characters before the last 'DOT' from the name.
	 * 
	 * @param name as the file name
	 * @return the extension of the file.
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
