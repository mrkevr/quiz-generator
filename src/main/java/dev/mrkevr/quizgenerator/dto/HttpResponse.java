package dev.mrkevr.quizgenerator.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HttpResponse {
	
	LocalDateTime timestamp;
	
	Integer status;
	
	String message;
	
	String downloadUrl;
}
