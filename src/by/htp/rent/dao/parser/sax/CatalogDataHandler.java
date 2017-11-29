package by.htp.rent.dao.parser.sax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.htp.rent.dao.domain.Equipment;

public class CatalogDataHandler extends DefaultHandler {

	private StringBuilder text;
	private Equipment equipment;
	private List<Equipment> equipments = new ArrayList<>();

	@Override
	public void startDocument() throws SAXException {
		System.out.println("startDocument");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("startElement=" + localName);

		switch (localName) {
		case "equipment":
			
			equipment = new Equipment();
			String idString = attributes.getValue("id");
			int id = Integer.parseInt(idString);
			equipment.setId(id);
			break;
		}

		text = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("endElement=" + localName);

		switch (localName) {
		case "equipment":

			equipments.add(equipment);
			break;
		case "title":
			equipment.setTitle(text.toString().trim());
			break;
		case "price":
			double price = Double.parseDouble(text.toString().trim());
			equipment.setPrice(price);
			break;
		case "date":
			
			try {
				String dateString = text.toString().trim();
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
				Date date = format.parse(dateString);
				equipment.setDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		text.append(ch, start, length);
		System.out.println("characters: " + text);
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

}
