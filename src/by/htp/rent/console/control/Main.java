package by.htp.rent.console.control;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.dao.domain.Catalog;
import by.htp.rent.dao.parser.dom.CatalogDataDomImpl;
import by.htp.rent.dao.parser.sTax.CatalogDataStaxImpl;
import by.htp.rent.dao.parser.sax.CatalogDataSaxImpl;

public class Main {

	public static void main(String[] args) {
		
//		CatalogData dao = new CatalogDataDomImpl();
		CatalogData dao = new CatalogDataStaxImpl();
//		CatalogData dao = new CatalogDataSaxImpl();
		Catalog catalog = dao.readCatalog();
		
		System.out.println(catalog);

	}

}
