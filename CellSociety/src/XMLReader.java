import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
	private String RESOURCE_PACKAGE_XML = "Resources/XMLTags";
	ResourceBundle myResources;
	
	public String readXMLFile(File file){
		String strsimInfo = new String();
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file); 
			doc.getDocumentElement().normalize();
			String whichSim = "";
			
		    myResources = ResourceBundle.getBundle(RESOURCE_PACKAGE_XML);

			
			NodeList nList = doc.getElementsByTagName(myResources.getString("SIMULATION"));
			for (int i=0; i<nList.getLength(); i++){
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					whichSim += getElements(eElement,"NAME");
					strsimInfo+=whichSim;
					strsimInfo+=(",") +getElements(eElement, "TITLE");
					strsimInfo+=(",") +getElements(eElement, "AUTHOR");
					strsimInfo+=(",") +getElements(eElement, "NUMSTATES");
					strsimInfo+=(",") +getElements(eElement, "DIMENSION");
					strsimInfo+=(",") +getElements(eElement, "INITIAL");
					strsimInfo+=(",") +getElements(eElement, "SETTING");
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return strsimInfo;
	}
	private String getElements(Element eElement, String s){
		return  (eElement.getElementsByTagName(myResources.getString(s)).item(0).getTextContent());
	}
}