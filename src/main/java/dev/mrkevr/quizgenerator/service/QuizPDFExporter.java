package dev.mrkevr.quizgenerator.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
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

	public void exportDocument(HttpServletResponse response, String id, String title) throws DocumentException, IOException {

		List<Question> questions = excelDataExtractor.extract(id);

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = this.getFont();
		
		// Add title if specified
		if (title != null) {
			Paragraph titleParagraph = new Paragraph(title, font);
			titleParagraph.setAlignment(Element.ALIGN_CENTER);
			document.add(titleParagraph);
			document.add(Chunk.NEWLINE);
		}
		
		// Header 
		Paragraph formParagraph = new Paragraph("Name : __________________________________________________   Date : _________________________   Sccore : ________", font);
		formParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(formParagraph);
		document.add(Chunk.NEWLINE);
		Paragraph directionParagraph = new Paragraph("Directions : Read the sentences carefully. Choose the letter of the best answer. Write your answer before the number", font);
		document.add(directionParagraph);
		document.add(Chunk.NEWLINE);

		// Questions
		int[] itemNumber = { 1 };
		questions.forEach(question -> {
			Paragraph questionStr = new Paragraph("______" + itemNumber[0] + ". " + question.getQuestion(), font);
			
			// Create 2 columns in table.
			PdfPTable table = new PdfPTable(2);
			
			// Add choices in table
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
