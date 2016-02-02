import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLReader {

	public void readXMLFile(File file){
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file); 
			doc.getDocumentElement().normalize();
			
			//name of simulation
			//title for simulation
			//author of simulation
			//settings for global configuration parameters
			//dimensions of grid and initial configuration of the states for cells
			
			}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}