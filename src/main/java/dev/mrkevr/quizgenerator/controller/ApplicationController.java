package dev.mrkevr.quizgenerator.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import dev.mrkevr.quizgenerator.dto.HttpResponse;
import dev.mrkevr.quizgenerator.service.FileUploader;
import dev.mrkevr.quizgenerator.service.QuizPDFExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

	private final QuizPDFExporter quizPDFExporter;
	private final FileUploader fileUploader;
	
	@Value("server.port")
	private String port;
	
	@PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<HttpResponse> generate(
			@RequestParam(name = "file", required = true) MultipartFile file, 
			@RequestParam(name = "title", required = false) String title,
			HttpServletResponse response)
			throws IOException {
		
		// Upload
		String fileId = fileUploader.uploadFile(file, UUID.randomUUID().toString());
		
		return ResponseEntity.ok(
			HttpResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.OK.value())
			.message("File uploaded and ready for download")
			.downloadUrl("http://localhost:8080/download?id=" + fileId + (title != null ? "&title=" + title : ""))
			.build());
	}

	@GetMapping(value = "/download")
	public ResponseEntity<HttpResponse> downloadQuiz(
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "id", required = true) String id, 
			HttpServletResponse response)	
			throws DocumentException, IOException {

		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=quiz_" + id + ".pdf";
		response.setHeader(headerKey, headerValue);
		quizPDFExporter.exportDocument(response, id, title);
		return null;
	}
}
