package dev.mrkevr.quizgenerator.service;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import dev.mrkevr.quizgenerator.dto.Question;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizPDFExporter {
	
	private final ExcelDataExtractor excelDataExtractor;
	
	public void exportDocument(HttpServletResponse response, File file)
			throws DocumentException, IOException {
		
		// Extract questions from the excel file
		List<Question> questions = excelDataExtractor.extract(file);

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = this.getFont();
		
		// Quiz Header
		Paragraph p1 = new Paragraph("Name : ", font);
		p1.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(p1);
		Paragraph p2 = new Paragraph("Date : ", font);
		p2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(p2);
		document.add(Chunk.NEWLINE);
		
		// Questions
		questions.forEach(question -> {
			Paragraph questionStr = new Paragraph(question.getQuestion(), font);
			document.add(questionStr);
			document.add(Chunk.NEWLINE);
		});

		document.close();
	}

	private Font getFont() {
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(10);
		font.setColor(Color.BLACK);
		return font;
	}
}
