package dev.mrkevr.quizgenerator;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import dev.mrkevr.quizgenerator.dto.Question;
import dev.mrkevr.quizgenerator.service.ExcelDataExtractor;

@SpringBootApplication
public class QuizGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizGeneratorApplication.class, args);
	}

//	@Bean
	CommandLineRunner runner(ExcelDataExtractor extractor) {
		return args -> {

			Resource resource = new ClassPathResource("files/questions.xlsx");
			File file = resource.getFile();
			
			List<Question> questions = extractor.extract(file);
			List<Question> limit = questions.stream().collect(Collectors.toList());

			limit.forEach(System.out::println);
		};
	}

}
