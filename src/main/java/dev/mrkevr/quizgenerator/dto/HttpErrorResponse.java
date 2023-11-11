package dev.mrkevr.quizgenerator.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpErrorResponse {
	
	private LocalDateTime timeStamp;

	private int status;

	private String error;
}
