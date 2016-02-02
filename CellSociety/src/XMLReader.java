import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

	public List<String> readXMLFile(File file){
		List<String> simInfo = new ArrayList<String>();
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file); 
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("simulation");
			for (int i=0; i<nList.getLength(); i++){
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					simInfo.add(eElement.getElementsByTagName("name").item(0).getTextContent());
					simInfo.add(eElement.getElementsByTagName("title").item(0).getTextContent());
					simInfo.add(eElement.getElementsByTagName("author").item(0).getTextContent());
					simInfo.add(eElement.getElementsByTagName("settings").item(0).getTextContent());
					simInfo.add(eElement.getElementsByTagName("dimension").item(0).getTextContent());
					simInfo.add(eElement.getElementsByTagName("Initial").item(0).getTextContent());
				}
			}
			//name of simulation
			//title for simulation
			//author of simulation
			//settings for global configuration parameters
			//dimensions of grid and initial configuration of the states for cells
			
			}
		catch (Exception e){
			e.printStackTrace();
		}
		return simInfo;
	}
}