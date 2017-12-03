package users;

import java.io.IOException;

import utils.XmlReader;

public class XmlUser {

	public static void main(String[] args) throws IOException {

		String fileName = "C:\\Users\\meher\\workspace\\XMLUtils\\src\\main\\resources\\PSO.xml";
		XmlReader xmlReader = new XmlReader();
		xmlReader.processXMLFile(fileName);
		// System.out.println(xmlReader.result);

		String xmlExpr = "//Payment[@id='Maestro']/PaymentCard/PaymentAuthorization/Token";
		String result = xmlReader.getNodeValue(xmlExpr, xmlReader.result);
		System.out.println("Node value :: " + xmlExpr + " is :: " + result);

		System.out.println("Number of elements : " + xmlReader.countNoOfElements("SalesOrder", xmlReader.result));

		System.out.println("Attribute value for a given xpath element : "
				+ xmlReader.getXmlAttributeValue("//CustomerParty/ID[1]", "schemeName", xmlReader.result));

		System.out.println("Atrributes : " + xmlReader.getAllAttributes("//CustomerParty/ID[1]", xmlReader.result));
	}

}
