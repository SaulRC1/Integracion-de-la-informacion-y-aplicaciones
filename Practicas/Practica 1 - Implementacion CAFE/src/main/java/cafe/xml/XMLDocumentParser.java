package cafe.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class XMLDocumentParser
{
    private static final DocumentBuilderFactory documentBuilderFactory
            = DocumentBuilderFactory.newInstance();

    private static final String NOT_XML_EXTENSION_MESSAGE = "The file does not"
            + "have .xml extension.";

    /**
     * Parses a XML document.
     *
     * @param pathToFile The path to the desired file, preferably an absolute
     * path.
     *
     * @return The parsed XML Document
     *
     * @throws ParserConfigurationException if a DocumentBuilder cannot be
     * created which satisfies the configuration requested.
     *
     * @throws SAXException If any parse error occurs.
     *
     * @throws IOException If any IO exception occurs.
     *
     * @throws cafe.xml.WrongDocumentExtensionException When the file does not
     * have .xml extension
     */
    public static Document parseXMLDocument(String pathToFile) throws ParserConfigurationException,
            SAXException, IOException, WrongDocumentExtensionException
    {
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Path documentPath = Paths.get(pathToFile).toAbsolutePath();

        if (!documentPath.toString().toLowerCase().trim().endsWith(".xml"))
        {
            throw new WrongDocumentExtensionException(NOT_XML_EXTENSION_MESSAGE);
        }

        Document xmlDocument = documentBuilder.parse(new File(documentPath.toString()));

        xmlDocument.getDocumentElement().normalize();

        return xmlDocument;
    }
    
    /**
     * Writes the {@link Document} into a XML document in the provided path.
     * 
     * @param xmlDocument The XML document.
     * @param path The file destination path.
     * @throws javax.xml.transform.TransformerConfigurationException
     * @throws java.io.IOException
     */
    public static void writeXMLDocument(Document xmlDocument, String path) 
            throws TransformerConfigurationException, IOException, TransformerException
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        DOMSource source = new DOMSource(xmlDocument);
        FileWriter writer = new FileWriter(new File(path + ".xml"));
        StreamResult result = new StreamResult(writer);
        
        transformer.transform(source, result);
    }

    /**
     * Will print the document tag and content of the node of a document
     * @param node the node that we want to print
     */
    public static void printDocument(Node node)
    {
        System.out.println(node.getNodeName());

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                //calls this method for all the children which is Element
                printDocument(currentNode);
            } else if (currentNode.getNodeType() == Node.TEXT_NODE)
            {
                // Print the text content of the node (if any)
                String textContent = currentNode.getTextContent().trim();
                if (!textContent.isEmpty())
                {
                    System.out.println("  Text Content: " + textContent);
                }
            }
        }
    }
}