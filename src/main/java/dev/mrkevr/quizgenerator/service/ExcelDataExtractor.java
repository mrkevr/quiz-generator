package dev.mrkevr.quizgenerator.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.poiji.bind.Poiji;

import dev.mrkevr.quizgenerator.dto.Question;
import dev.mrkevr.quizgenerator.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelDataExtractor {

	private final FileUploader fileUploader;

	public List<Question> extract(String fileName) {

		if (!fileUploader.fileExists(fileName)) {
			throw new FileNotFoundException(fileName);
		}

		Resource resource = new ClassPathResource("files/" + fileName + ".xlsx");
		try {
			File file = resource.getFile();
			return Poiji.fromExcel(file, Question.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}