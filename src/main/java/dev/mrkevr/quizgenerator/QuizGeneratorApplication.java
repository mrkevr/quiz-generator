package dev.mrkevr.quizgenerator;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import dev.mrkevr.quizgenerator.service.ExcelDataExtractor;

@SpringBootApplication
public class QuizGeneratorApplication {
	
	@Autowired
	ResourceLoader resourceLoader;
	
	public static void main(String[] args) {
		SpringApplication.run(QuizGeneratorApplication.class, args);
	}

//	@Bean
	CommandLineRunner runner(ExcelDataExtractor extractor) {
		return args -> {
			
			Resource resource = resourceLoader.getResource("classpath:files");
			File file = resource.getFile();
			System.out.println(file.getAbsolutePath());
			
			
//			Resource resource = new ClassPathResource("files/questions.xlsx");
//			
//			File file = resource.getFile();
//			
//			List<Question> questions = extractor.extract(file);
//			List<Question> limit = questions.stream().collect(Collectors.toList());
//
//			limit.forEach(System.out::println);
		};
	}
}
