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
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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
			
			// Create 2 columns in table.
			PdfPTable table = new PdfPTable(2);
			// Add cells in table
			table.addCell(this.convertToChoiceCell(new Paragraph("a) " + question.getA(), font)));
			table.addCell(this.convertToChoiceCell(new Paragraph("b) " + question.getB(), font)));
			table.addCell(this.convertToChoiceCell(new Paragraph("c) " + question.getC(), font)));
			table.addCell(this.convertToChoiceCell(new Paragraph("d) " + question.getD(), font)));

			document.add(questionStr);
			document.add(table);
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

	private PdfPCell convertToChoiceCell(Paragraph paragraph) {
		// Create cell
		PdfPCell cell = new PdfPCell(paragraph);
		// Remove border from the cell
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
}
