package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author meher
 *
 */
public class XmlReader {

	// read xml from string
	// count number of elements using xpath
	// get attribute value using xpath
	// get nodevalue using xpath

	private String filename;
	private String xmlString;
	public String result;

	public XmlReader() {

	}

	public void processXMLFile(String fileName) throws IOException {
		this.xmlString = readFile(fileName);
		this.result = removeXmlStringNamespaceAndPreamble(xmlString);
	}

	/**
	 * removes namespaces form xml file content
	 * 
	 * @param xmlString
	 * @return xml file content without namespaces
	 */
	public String removeXmlStringNamespaceAndPreamble(String xmlString) {
		return xmlString.replaceAll("(<\\?[^<]*\\?>)?", ""). /* remove preamble */
				replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
				.replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
				.replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
	}

	/**
	 * Converts xmlfile content into a string
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public String getXmlAttributeValue(String xpathExpr, String attributeName, String xmlString) {
		String result = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document document = db.parse(is);
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.evaluate(xpathExpr, document, XPathConstants.NODE);
			if (node.getAttributes().getNamedItem(attributeName) != null) {
				result = node.getAttributes().getNamedItem(attributeName).getNodeValue();
				return result;
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getNodeValue(String xpathExpr, String xmlString) {
		String result = null;
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document dDoc = builder.parse(is);

			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.evaluate(xpathExpr, dDoc, XPathConstants.NODE);
			return result = node.getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int countNoOfElements(String xmlElement, String xmlString) {
		int result = 0;

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document doc = docBuilder.parse(is);

			NodeList list = doc.getElementsByTagName(xmlElement);

			result = list.getLength();
			return result;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}

		return result;
	}

	public ArrayList<String> getAllAttributes(String xpathExpr, String xmlString) {

		ArrayList<String> result = new ArrayList<String>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document document = db.parse(is);
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.evaluate(xpathExpr, document, XPathConstants.NODE);
			NamedNodeMap attributes = node.getAttributes();

			for (int i = 0; i < attributes.getLength(); i++) {
				Attr attr = (Attr) attributes.item(i);
				String attrName = attr.getNodeName();
				String attrValue = attr.getNodeValue();

				result.add(attrName + " : " + attrValue);

			}
			return result;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}
}
