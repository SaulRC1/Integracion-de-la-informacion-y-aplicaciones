package test.cafe.services;

import cafe.models.message.Message;
import cafe.models.port.EntryExitPort;
import cafe.models.slot.Slot;
import cafe.services.ColdDrinksBarman;
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
public class ColdDrinksBarmanTest
{

    @Test
    public void test_format_drink_information()
    {
        Slot outputSlot = new Slot();
        Slot inputSlot = new Slot();

        EntryExitPort entryExitPort = new EntryExitPort(outputSlot, inputSlot);

        ColdDrinksBarman coldDrinksBarman = new ColdDrinksBarman(entryExitPort);

        Document cocaColaDrinkInfo = coldDrinksBarman.formatDrinkInformation("coca-cola");

        XMLDocumentParser.printDocument(cocaColaDrinkInfo.getDocumentElement());
    }

    @Test
    public void test_functionality()
    {
        try
        {
            Slot outputSlot = new Slot();
            Slot inputSlot = new Slot();

            EntryExitPort entryExitPort = new EntryExitPort(outputSlot, inputSlot);

            ColdDrinksBarman coldDrinksBarman = new ColdDrinksBarman(entryExitPort);

            Document messageMetadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DrinkBarmanTranslatorTest/message_metadata_cold_drink.xml");
            Document messageBody = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DrinkBarmanTranslatorTest/message_body_cold_drink.xml");

            Message msg = new Message(messageBody, messageMetadata);
            
            entryExitPort.writeIntoInputSlot(msg);
            
            coldDrinksBarman.performFunctionality();
            
            Message coldDrinksBarmanOutputMessage = entryExitPort.readFromOutputSlot();
            
            XMLDocumentParser.printDocument(coldDrinksBarmanOutputMessage.getDocument().getDocumentElement());
            
            XMLDocumentParser.printDocument(coldDrinksBarmanOutputMessage.getDocumentMetaData().getDocumentElement());
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(ColdDrinksBarmanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
