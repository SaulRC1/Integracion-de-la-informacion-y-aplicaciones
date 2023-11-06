/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package cafe.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
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
        
        if(!documentPath.toString().toLowerCase().trim().endsWith(".xml"))
        {
            throw new WrongDocumentExtensionException(NOT_XML_EXTENSION_MESSAGE);
        }

        Document xmlDocument = documentBuilder.parse(new File(documentPath.toString()));

        xmlDocument.getDocumentElement().normalize();

        return xmlDocument;
    }
}
