package dev.mrkevr.quizgenerator.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

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
	
	@PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<HttpResponse> generate(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws IOException {

		// File validation
		if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xlsx")) {
			return ResponseEntity.badRequest().body(
					HttpResponse.builder()
					.timestamp(LocalDateTime.now())
					.status(HttpStatus.BAD_REQUEST.value())
					.message("Please upload an Excel(.xlsx) file.")
					.build()
					);
		}
		
		// Upload
		String fileId = fileUploader.uploadFile(file, UUID.randomUUID().toString());
		
		return ResponseEntity.ok(
			HttpResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.OK.value())
			.message("File uploaded and ready for download")
			.body(Map.of("Download URL", "http://localhost:8080/download?id=" + fileId))
			.build());
	}

	@GetMapping(value = "/download")
	public ResponseEntity<HttpResponse> downloadQuiz(@RequestParam(name = "id", required = true) String id, HttpServletResponse response)	
			throws DocumentException, IOException {

		if (!fileUploader.fileExists(id)) {
			return ResponseEntity.badRequest().body(
				HttpResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.message("No file found with that id")
				.build());
		}
		
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=quiz_" + id + ".pdf";
		response.setHeader(headerKey, headerValue);
		quizPDFExporter.exportDocument(response, id);
		return null;
	}
}
