package es.ucm.fdi.iw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Table.Cell;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.ucm.fdi.iw.constants.ConstantsPdfFile;
import es.ucm.fdi.iw.model.StClass;
import es.ucm.fdi.iw.model.User;

/**
 * Generates a PDF with the information of the students from a class
 * @author aitorcay
 */

public class PdfGenerator {
	
	private static final Logger log = LogManager.getLogger(PdfGenerator.class);

	/**
	 * Genera un fichero PDF con la información de los estudiantes de una clase
	 * 
	 * @param users		lista de alumnos
	 * @param stClass	clase
	 * @return			nombre del fichero
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String generateQrClassFile(List<User> users, StClass stClass) throws DocumentException, MalformedURLException, IOException {
		
		File directory = new File(ConstantsPdfFile.QR_DIR);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
		
		String qrFile = ConstantsPdfFile.QR_DIR + ConstantsPdfFile.QR_FILE + "." + ConstantsPdfFile.PDF;
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(qrFile));
		Font font = FontFactory.getFont(FontFactory.COURIER, ConstantsPdfFile.QR_FONT_SIZE, BaseColor.BLACK);
		PdfPTable table;
		
		document.open();				

		int numPages;
		if (users.size() % ConstantsPdfFile.NUM_ROWS == 0) {
			numPages = users.size() / ConstantsPdfFile.NUM_ROWS;
		} else {
			numPages = (users.size() / ConstantsPdfFile.NUM_ROWS) + 1;
		}

		for (int i=0; i < numPages; i++) {
			for (int j=0; j < Math.min(ConstantsPdfFile.NUM_ROWS, users.size()-i*ConstantsPdfFile.NUM_ROWS); j++) {
				int cellCount = ConstantsPdfFile.NUM_ROWS*i+j;
				User u = users.get(cellCount);
				String name = u.getFirstName() + ",\n" + u.getLastName();
				String img = ConstantsPdfFile.QR_DIR + ConstantsPdfFile.QR_IMG + u.getUsername() + "." + ConstantsPdfFile.PNG;
				
				//Para cada usuario se incluye: nombre y apellidos, nombre de usuario y código QR de acceso
				log.info("Creando QR para el usuario {}", cellCount);
				table = new PdfPTable(ConstantsPdfFile.NUM_COLS);
				table.getDefaultCell().setFixedHeight(ConstantsPdfFile.CELL_H);
				table.setWidths(new float[] {ConstantsPdfFile.NAME_W, ConstantsPdfFile.USER_W, ConstantsPdfFile.QR_W});
				table.addCell(new Phrase(name, font));
				table.addCell(new Phrase(u.getUsername(), font));
				QrGenerator.generateQrCode(u);
				Image qrCode = Image.getInstance(img);
				table.addCell(qrCode);
				table.completeRow();
				document.add(table);
			}
			
			document.newPage();
		}
		
		document.close();
		
		return qrFile;
	}
	
	
}
