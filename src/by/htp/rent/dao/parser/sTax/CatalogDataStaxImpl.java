package by.htp.rent.dao.parser.sTax;

import static by.htp.rent.dao.parser.DataTypeTransformUtil.convertDate;
import static by.htp.rent.dao.parser.DataTypeTransformUtil.convertId;
import static by.htp.rent.dao.parser.DataTypeTransformUtil.convertPrice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.dao.domain.Catalog;
import by.htp.rent.dao.domain.Equipment;
import by.htp.rent.dao.parser.CatalogTagName;

public class CatalogDataStaxImpl implements CatalogData {

	private static final String XML_FILE_PATH = "resources/rent_station.xml";
	private static final char UNDERSCORE = '_';
	private static final char DASH = '-';
	private static final String ID = "id";

	@Override
	public Catalog readCatalog() {
		Catalog catalog = new Catalog();

		XMLInputFactory xmlIF = XMLInputFactory.newInstance();

		InputStream stream;

		try {

			stream = new FileInputStream(XML_FILE_PATH);
			XMLStreamReader reader = xmlIF.createXMLStreamReader(stream);

			List<Equipment> equipments = processReader(reader);
			catalog.setEquipments(equipments);

		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}

		return catalog;
	}

	private List<Equipment> processReader(XMLStreamReader reader) {
		List<Equipment> equipments = new ArrayList<>();

		Equipment equipment = null;
		CatalogTagName tag = null;

		try {
			while (reader.hasNext()) {
				int type = reader.next();

				switch (type) {

				case XMLStreamConstants.START_ELEMENT:

					tag = getTag(reader.getLocalName());

					switch (tag) {
					case EQUIPMENT:
						equipment = new Equipment();
						String id = reader.getAttributeValue(null, ID);
						equipment.setId(convertId(id));
						equipments.add(equipment);
						break;

					}

				case XMLStreamConstants.CHARACTERS:
					
					String text = reader.getText().trim();

					if (text.isEmpty()) {
						break;
					}

					switch (tag) {
					case TITLE:
						equipment.setTitle(text);
						break;
					case PRICE:
						equipment.setPrice(convertPrice(text));
						break;
					case DATE:
						equipment.setDate(convertDate(text));
						break;
					}

				case XMLStreamConstants.END_ELEMENT:

					break;

				}

			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		return equipments;
	}

	private CatalogTagName getTag(String localName) {
		String tag = localName.toUpperCase().replace(DASH, UNDERSCORE);
		CatalogTagName tagElement = CatalogTagName.valueOf(tag);
		return tagElement;
	}

}
