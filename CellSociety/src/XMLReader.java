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
	private static final String SEG = "Segregation";
	private static final String GOL = "Game_of_Life";
	private static final String PP = "Predator_Prey";
	private static final String SF = "Spreading_Fire";
	private static final String SIMULATION = "simulation";
	private static final String NAME = "name";
	private static final String TITLE = "title";
	private static final String AUTHOR = "author";
	private static final String NUMSTATES = "numstates";
	private static final String PERCENTAGE = "percentage";
	private static final String REPRODUCE = "reproduce";
	private static final String STARTENERGY = "startEnergy";
	private static final String FISHENERGY = "fishEnergy";
	private static final String PROBABILITY = "probability";
	private static final String DIMENSION = "dimension";
	private static final String INITIAL = "Initial";
	
	public String readXMLFile(File file){
		String strsimInfo = new String();
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file); 
			doc.getDocumentElement().normalize();
			String whichSim = "";
			NodeList nList = doc.getElementsByTagName(SIMULATION);
			for (int i=0; i<nList.getLength(); i++){
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					whichSim += (eElement.getElementsByTagName(NAME).item(0).getTextContent());
					strsimInfo+=whichSim;
					strsimInfo+=((",") + (eElement.getElementsByTagName(TITLE).item(0).getTextContent()));
					strsimInfo+=((",") + (eElement.getElementsByTagName(AUTHOR).item(0).getTextContent()));
					strsimInfo+=((",") + (eElement.getElementsByTagName(NUMSTATES).item(0).getTextContent()));
					strsimInfo+=((",") + (eElement.getElementsByTagName(DIMENSION).item(0).getTextContent()));
					strsimInfo+=((",") + (eElement.getElementsByTagName(INITIAL).item(0).getTextContent()));
					if(whichSim.equals(SEG)){ 
						strsimInfo+=((",") + (eElement.getElementsByTagName(PERCENTAGE).item(0).getTextContent()));
					}
					if(whichSim.equals(PP)){
						strsimInfo+=((",") + (eElement.getElementsByTagName(REPRODUCE).item(0).getTextContent()));
						strsimInfo+=((",") + (eElement.getElementsByTagName(STARTENERGY).item(0).getTextContent()));
						strsimInfo+=((",") + (eElement.getElementsByTagName(FISHENERGY).item(0).getTextContent()));
					}
						
					if(whichSim.equals(SF)){
						strsimInfo+=((",") + (eElement.getElementsByTagName(PROBABILITY).item(0).getTextContent()));					
					}				
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return strsimInfo;
	}
}