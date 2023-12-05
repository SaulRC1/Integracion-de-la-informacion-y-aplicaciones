package test.cafe.models.task.modifying;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.modifying.ContextEnricherTask;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ContextEnricherTaskTest
{
    @Test
    public void testDoTask()
    {
        Slot inputSlot = new Slot();
        Slot outPutSlot = new Slot();
        Slot contextSlot = new Slot();

        try
        {
            Document sample1_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/ContextEnricherTest/sample1_1.xml");
            Document sample1_1_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/ContextEnricherTest/sample1_1_1.xml");

            MessageBuilder factory = new MessageBuilder();
            Message message = factory.buildMessageWithEmptyMetadata(sample1_1);
            Message message2 = factory.buildMessageWithEmptyMetadata(sample1_1_1);
            inputSlot.write(message);
            contextSlot.write(message2);

            ContextEnricherTask contextEnricher = new ContextEnricherTask(inputSlot, outPutSlot, contextSlot, "drink");

            contextEnricher.doTask();

            List<Message> outputMessages = outPutSlot.getMessages();
            assertEquals(1, outputMessages.size());
            int i = 0;
            for (Message outputMessage : outputMessages)
            {
                Document messageBody = outputMessage.getDocument();
                Document messageMetadata = outputMessage.getDocumentMetaData();
                System.out.println("Mensaje " + i++ + ":");
                XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
                XMLDocumentParser.printDocument(messageBody.getDocumentElement());
            }
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(ContextEnricherTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}