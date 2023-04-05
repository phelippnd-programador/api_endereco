package br.com.phdigitalcode.erp_endereco.domain.handler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import br.com.phdigitalcode.erp_endereco.domain.model.vo.planilha.Cell;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.planilha.Row;
import lombok.Getter;

public class SheetHandler extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int celula = 0;
	@Getter
	private List<Row> rows;
	private Row linha;

	
	public SheetHandler() {
		rows = new ArrayList<Row>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// c => cell
//		System.out.println("START - "+qName);
		if (qName.equals("row")) {
			linha = new Row();
		}
		if (qName.equals("c")) {
			// Print the cell reference
//			System.out.print(attributes.getValue("r") + " - ");
			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue("t");
			if (cellType != null && cellType.equals("s")) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		// Clear contents cache
		lastContents = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
//		System.out.println("END - " + qName);
		// Process the last contents as required.
		// Do now, as characters() may be called more than once
		if (nextIsString) {
			int idx = Integer.parseInt(lastContents);
			lastContents = sst.getItemAt(idx).getString();
			nextIsString = false;
		}
		// v => contents of a cell
		// Output after we've seen the string contents
		if (qName.equals("v")) {
			Cell cell = new Cell(celula, lastContents);
			linha.add(cell);
		}
		if (qName.equals("c")) {
			celula++;
		}
		if (qName.equals("row")) {
			celula = 0;
			rows.add(linha);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	public void processSheets(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		this.sst = (SharedStringsTable) r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser();

		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	public XMLReader fetchSheetParser() throws SAXException, ParserConfigurationException {
		XMLReader parser = XMLHelper.newXMLReader();
		parser.setContentHandler(this);
		return parser;
	}

}
