package test.cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import cafe.xml.XPathParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class AggregatorTaskTest
{

    @Test
    public void test()
    {
        try
        {
            Document sample1Part1 = XMLDocumentParser.parseXMLDocument("src/main/resources/AggregatorTaskTest/sample1_1.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document sample1Part1Metadata = docBuilder.newDocument();
            
            Element rootElement = sample1Part1Metadata.createElement("metadata");
            sample1Part1Metadata.appendChild(rootElement);
            
            XPathParser xpathParser = new XPathParser();
            
            Node sample1Part1SplitterId = (Node) xpathParser.executeXPathExpression(
                    "/document/metadata/splitterId", sample1Part1, 
                    XPathConstants.NODE);
            
            Node imported = sample1Part1Metadata.importNode(sample1Part1SplitterId, true);
            rootElement.appendChild(imported);
            
            XMLDocumentParser.printDocument(sample1Part1Metadata.getDocumentElement());
//Message msg = new Message(document, documentMetaData)
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
