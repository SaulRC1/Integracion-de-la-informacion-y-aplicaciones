package test.cafe.services;

import cafe.models.message.Message;
import cafe.models.port.ExitPort;
import cafe.models.slot.Slot;
import cafe.services.Waiter;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class WaiterTest 
{
    @Test
    public void test()
    {
        try
        {
            String destinationFilePath = "C:\\Users\\SaulRC1\\Documents\\4º Ingeniería Informática\\IIA\\waiter_order";
            
            Slot exitPortSlot = new Slot();
            ExitPort exitPort = new ExitPort(exitPortSlot);
            
            Waiter waiter = new Waiter(exitPort, destinationFilePath);
            
            Document messageBody = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/WaiterTest/incoming_order_body.xml");
            
            Document messageMetadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/WaiterTest/incoming_order_metadata.xml");
            
            Message msg = new Message(messageBody, messageMetadata);
            
            exitPort.write(msg);
            
            waiter.performFunctionality();
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(WaiterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
