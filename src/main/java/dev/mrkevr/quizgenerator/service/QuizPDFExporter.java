package dev.mrkevr.quizgenerator.service;

import java.awt.Color;
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

	public void exportDocument(HttpServletResponse response, String id) throws DocumentException, IOException {

		List<Question> questions = excelDataExtractor.extract(id);

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
		int[] itemNumber = { 1 };
		questions.forEach(question -> {
			Paragraph questionStr = new Paragraph(itemNumber[0] + ". " + question.getQuestion(), font);
			Paragraph a = new Paragraph("a. " + question.getA(), font);
			a.setIndentationLeft(15);
			Paragraph b = new Paragraph("b. " + question.getB(), font);
			b.setIndentationLeft(15);
			Paragraph c = new Paragraph("c. " + question.getC(), font);
			c.setIndentationLeft(15);
			Paragraph d = new Paragraph("d. " + question.getD(), font);
			d.setIndentationLeft(15);
			document.add(questionStr);
			document.add(a);
			document.add(b);
			document.add(c);
			document.add(d);
			document.add(Chunk.NEWLINE);
			itemNumber[0]++;
		});

		document.close();
	}

	private Font getFont() {
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(8);
		font.setColor(Color.BLACK);
		return font;
	}
}
