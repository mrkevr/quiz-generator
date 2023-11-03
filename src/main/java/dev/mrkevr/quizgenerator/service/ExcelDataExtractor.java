package dev.mrkevr.quizgenerator.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.poiji.bind.Poiji;

import dev.mrkevr.quizgenerator.dto.Question;

@Service
public class ExcelDataExtractor {

	public List<Question> extract(String filePath) {
		return Poiji.fromExcel(new File(filePath), Question.class);
	}

	public List<Question> extract(File file) {
		return Poiji.fromExcel(file, Question.class);
	}
	
}
