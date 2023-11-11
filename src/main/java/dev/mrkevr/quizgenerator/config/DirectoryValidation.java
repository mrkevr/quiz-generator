package dev.mrkevr.quizgenerator.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DirectoryValidation implements ApplicationRunner {

	@Value("${file.directory}")
	private String directoryPath;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// Check if the directory exists and is a directory
		Path path = Paths.get(directoryPath);
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			throw new IllegalStateException("Invalid directory configured: " + directoryPath);
		}
		
		// Additional validation or initialization logic can be added here
	}

}
