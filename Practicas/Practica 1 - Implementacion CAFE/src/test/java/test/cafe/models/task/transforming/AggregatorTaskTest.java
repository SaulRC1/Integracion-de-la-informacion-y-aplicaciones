package test.cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
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
            
            
            //Message msg = new Message(document, documentMetaData)
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
