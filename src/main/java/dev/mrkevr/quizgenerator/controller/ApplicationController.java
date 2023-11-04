package dev.mrkevr.quizgenerator.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import dev.mrkevr.quizgenerator.service.FileUploader;
import dev.mrkevr.quizgenerator.service.QuizPDFExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

	private final QuizPDFExporter quizPDFExporter;
	private final FileUploader fileUploader;

	@PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String generate(@RequestParam("file") MultipartFile file, HttpServletResponse response) 
			throws IOException {

		// validate file
		if (file.isEmpty()) {
			return "Please select an Excel file to upload.";
		}
		if (!file.getOriginalFilename().endsWith(".xlsx")) {
			return "Please upload a correct file type(.xlsx)";
		}

		String savedFileName = fileUploader.uploadFile(file, UUID.randomUUID().toString());
		return "File uploaded and ready for download. Quiz Id = " + savedFileName;
	}

	@GetMapping(value = "/download")
	public void downloadQuiz(@RequestParam("id") String id, HttpServletResponse response)
			throws DocumentException, IOException {
		
		
		
		
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=quiz_" + id + ".pdf";
		response.setHeader(headerKey, headerValue);
		quizPDFExporter.exportDocument(response, id);
	}

//	private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
//		File file = new File("mrkever_" + multipartFile.getOriginalFilename());
//		if (file.createNewFile()) {
//			try {
//				multipartFile.transferTo(file);
//				return file;
//			} catch (IOException e) {
//				throw new IOException("Failed to convert MultipartFile to File: " + e.getMessage());
//			}
//		} else {
//			throw new IOException("Failed to create a new file for MultipartFile.");
//		}
//	}
//
//	private File getDummyFile() throws IOException {
//		Resource resource = new ClassPathResource("files/questions.xlsx");
//		File file = resource.getFile();
//		return file;
//	}
}
