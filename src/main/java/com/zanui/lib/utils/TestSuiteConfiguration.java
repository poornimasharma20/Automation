package com.zanui.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestSuiteConfiguration {
	
	public static String xmlFilePath = System.getProperty ("user.dir") + File.separator + "testng.xml";
	
	/*************************************************************************
	* Objective: Main function from which the script starts executing
	* Parameters: args (String [])
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/

	public static void main(String[] args) throws Exception {

		XlsReader configSetting = new XlsReader(
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator + "config" + File.separator + "SuiteConfigurationSettings.xlsx"); 
		String testName = null; 
		String executionCondition = null; 
		int rowCount = configSetting.getRowCount("Execution Config");
		
		removeXMLelements();
		
		try {
			// Create document of Testng.xml 
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder(); 
			Document document = documentBuilder.newDocument();

			Element suiteName = document.createElement("suite"); 
			suiteName.setAttribute("name", "ZANUI AUTOMATION TEST SUITE"); 
			suiteName.setAttribute("preserve-order","true"); 
			suiteName.setAttribute("configfailurepolicy", "continue"); 
			document.appendChild(suiteName); 
			for (int i = 3; i <= rowCount; i++) {
				
				testName = configSetting.getCellData("Execution Config", 0, i); 
				executionCondition = configSetting.getCellData("Execution Config", 1, i); 
				if (executionCondition.equals("YES")) {
					addXMLType(document, suiteName, testName);
				}

			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			transformer.transform(domSource, streamResult);
			System.out.println("Done creating the XML File");
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	/*************************************************************************
	* Objective: To remove XML element
	* Parameters: None
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
		
	public static void removeXMLelements() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false); 
		DocumentBuilder db = dbf.newDocumentBuilder();
		System.out.println("xmlFilePath"+xmlFilePath);
		Document doc = db.parse(new FileInputStream(new File(xmlFilePath))); 
		removeRecursively(doc, Node.ELEMENT_NODE, "test");
		removeRecursively(doc, Node.COMMENT_NODE, null);
		doc.normalize();
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance(); 
		Transformer transformer = transformerFactory.newTransformer(); 
		DOMSource source = new DOMSource (doc); 
		StreamResult result = new StreamResult(new File("testng.xml")); 
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		transformer.transform(source, result); 
		System.out.println("XML file updated successfully"); 
		prettyPrint(doc);
		
	}
	
	/*************************************************************************
	* Objective: Remove all XML elements recursively
	* Parameters: node (Node), nodeType (short), name (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	
	public static void removeRecursively (Node node, short nodeType, String name) { 
		if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
		node.getParentNode().removeChild(node); 
		} else {
		NodeList list = node.getChildNodes(); 
		for (int i = 0; i < list.getLength(); i++) {
		removeRecursively(list.item(i), nodeType, name);
		}
	}
}
	/*************************************************************************
	* Objective: To create the testNG XML
	* Parameters:
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	
	public static final void prettyPrint(Document xml) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer(); 
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
		tf.setOutputProperty(OutputKeys.INDENT, "yes"); 
		Writer out = new StringWriter(); 
		tf. transform(new DOMSource(xml), new StreamResult(out)); 
		System.out.println(out.toString());
		}

	/*************************************************************************
	* Objective: Add XML tags for the testNG XML
	* Parameters: document (Document), suiteName (Element), testName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public static void addXMLType (Document document, Element suiteName, String testName) {
		String[] arrOfstr = testName.split("_");
		Element test = document.createElement("test"); 
		test.setAttribute("name", testName); 
		suiteName.appendChild(test);
		Element classes = document.createElement("classes"); 
		test.appendChild(classes); 
		Element className = document.createElement("class"); 
		className.setAttribute("name", "com.zanui.testScripts" + "." + testName); 
		classes.appendChild(className);
		}
}
