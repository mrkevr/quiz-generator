package dev.mrkevr.quizgenerator.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.poiji.bind.Poiji;

import dev.mrkevr.quizgenerator.dto.Question;
import dev.mrkevr.quizgenerator.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelDataExtractor {

	private final FileUploader fileUploader;

	@Value("${file.directory}")
	private String UPLOAD_DIR;

	public List<Question> extract(String fileName) throws IOException {

		if (!fileUploader.fileExists(fileName)) {
			throw new FileNotFoundException(fileName);
		}

		// Resource resource = new ClassPathResource("files/" + fileName + ".xlsx");
		// File file = resource.getFile();
		File file = new File(UPLOAD_DIR + "/" + fileName + ".xlsx");
		return Poiji.fromExcel(file, Question.class);
	}
}