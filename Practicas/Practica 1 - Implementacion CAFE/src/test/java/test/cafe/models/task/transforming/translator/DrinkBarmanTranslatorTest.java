package test.cafe.models.task.transforming.translator;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.models.task.transforming.TranslatorTask;
import cafe.models.task.transforming.translator.DrinksBarmanTranslator;
import cafe.models.task.transforming.translator.Translator;
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
public class DrinkBarmanTranslatorTest
{

    @Test
    public void test()
    {
        try
        {
            Document messageMetadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DrinkBarmanTranslatorTest/message_metadata.xml");
            Document messageBody = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DrinkBarmanTranslatorTest/message_body.xml");
            
            Message msg = new Message(messageBody, messageMetadata);
            
            Slot inputSlot = new Slot();
            Slot outputSlot = new Slot();
            
            inputSlot.write(msg);
            
            Translator drinksBarmanTranslator = new DrinksBarmanTranslator();
            
            TranslatorTask translatorTask = new TranslatorTask(inputSlot, outputSlot, drinksBarmanTranslator);
            
            translatorTask.doTask();
            
            Message translatedMessage = outputSlot.read();
            
            XMLDocumentParser.printDocument(translatedMessage.getDocument().getDocumentElement());
            XMLDocumentParser.printDocument(translatedMessage.getDocumentMetaData().getDocumentElement());
           
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(DrinkBarmanTranslatorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
