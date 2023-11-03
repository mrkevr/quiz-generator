package dev.mrkevr.quizgenerator.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import dev.mrkevr.quizgenerator.service.QuizPDFExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

	private final QuizPDFExporter quizPDFExporter;

	@PostMapping(value = "/generate", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String generate(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws DocumentException, IOException {

		// validate file
		if (file.isEmpty()) {
			return "Please select an Excel file to upload.";
		}
		if (!file.getOriginalFilename().endsWith(".xlsx")) {
			return "Please upload a correct file type(.xlsx)";
		}

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=quiz_" + currentDateTime + ".pdf";

		response.setHeader(headerKey, headerValue);

		quizPDFExporter.exportDocument(response, this.convertMultipartFileToFile(file));
		return "File uploaded and processed successfully.";
	}

	private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
		File file = new File("mrkever_" + multipartFile.getOriginalFilename());
		if (file.createNewFile()) {
			try {
				multipartFile.transferTo(file);
				return file;
			} catch (IOException e) {
				throw new IOException("Failed to convert MultipartFile to File: " + e.getMessage());
			}
		} else {
			throw new IOException("Failed to create a new file for MultipartFile.");
		}
	}

	private File getDummyFile() throws IOException {
		Resource resource = new ClassPathResource("files/questions.xlsx");
		File file = resource.getFile();
		return file;
	}
}
