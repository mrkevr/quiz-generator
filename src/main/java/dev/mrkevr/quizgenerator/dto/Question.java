package dev.mrkevr.quizgenerator.dto;

import com.poiji.annotation.ExcelCellName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

	@ExcelCellName("question")
	String question;

	@ExcelCellName("a")
	String a;

	@ExcelCellName("b")
	String b;

	@ExcelCellName("c")
	String c;

	@ExcelCellName("d")
	String d;
}
