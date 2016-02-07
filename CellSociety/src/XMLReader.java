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

/**
 * XMLReader serves to read XML files and return a concatenated string of relevant information extracted 
 * from the XML file. Its readXMLFile method interacts with UserInterface class
 * 
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class XMLReader {
    private String RESOURCE_PACKAGE_XML = "Resources/XMLTags";
    ResourceBundle myResources;

    /**
     * Called from UserInterface class. This method gets the loaded file and returns a concatenated
     * String of information separated by ','. It frequently calls getElements method which is implemented
     * within the class. 
     * @param file
     * @return
     */
    public String readXMLFile (File file) {
        String strsimInfo = new String();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            String whichSim = "";

            myResources = ResourceBundle.getBundle(RESOURCE_PACKAGE_XML);

            NodeList nList = doc.getElementsByTagName(myResources.getString("SIMULATION"));
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    whichSim += getElements(eElement, "NAME");
                    strsimInfo += whichSim;
                    strsimInfo += (",") + getElements(eElement, "TITLE");
                    strsimInfo += (",") + getElements(eElement, "AUTHOR");
                    strsimInfo += (",") + getElements(eElement, "NUMSTATES");
                    strsimInfo += (",") + getElements(eElement, "DIMENSION");
                    strsimInfo += (",") + getElements(eElement, "INITIAL");
                    strsimInfo += (",") + getElements(eElement, "SETTING");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // TODO check this out
        }
        return strsimInfo;
    }

    /**
     * getElements serves to avoid repeated codes that get Elements in XML by tag Name. By using resources folder,
     * mistakes from string input can be avoided.
     * @param eElement
     * @param s
     * @return
     */
    private String getElements (Element eElement, String s) {
        return (eElement.getElementsByTagName(myResources.getString(s)).item(0).getTextContent());
    }
}
