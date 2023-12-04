package test.cafe.services;

import cafe.models.message.Message;
import cafe.models.port.EntryExitPort;
import cafe.models.slot.Slot;
import cafe.services.ColdDrinksBarman;
import cafe.services.HotDrinksBarman;
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
public class HotDrinksBarmanTest 
{
    @Test
    public void test_functionality()
    {
        try
        {
            Slot outputSlot = new Slot();
            Slot inputSlot = new Slot();

            EntryExitPort entryExitPort = new EntryExitPort(outputSlot, inputSlot);

            HotDrinksBarman hotDrinksBarman = new HotDrinksBarman(entryExitPort);

            Document messageMetadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DrinkBarmanTranslatorTest/message_metadata.xml");
            Document messageBody = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DrinkBarmanTranslatorTest/message_body.xml");

            Message msg = new Message(messageBody, messageMetadata);
            
            entryExitPort.writeIntoInputSlot(msg);
            
            hotDrinksBarman.performFunctionality();
            
            Message hotDrinksBarmanOutputMessage = entryExitPort.readFromOutputSlot();
            
            XMLDocumentParser.printDocument(hotDrinksBarmanOutputMessage.getDocument().getDocumentElement());
            
            XMLDocumentParser.printDocument(hotDrinksBarmanOutputMessage.getDocumentMetaData().getDocumentElement());
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(ColdDrinksBarmanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
