package test.cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.routing.MergerTask;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.ArrayList;
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
public class MergerTaskTest
{

    @Test
    public void doTask()
    {
        Slot inputSlot1 = new Slot();
        Slot inputSlot2 = new Slot();
        Slot outPutSlot = new Slot();
        List<Slot> inputSlots = new ArrayList<>();
        inputSlots.add(inputSlot1);
        inputSlots.add(inputSlot2);

        try
        {
            Document sample1_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/ContextEnricherTest/sample1_1.xml");
            Document sample1_2 = XMLDocumentParser.parseXMLDocument("src/main/resources/SplitterTaskTest/sample1_1.xml");

            MessageBuilder factory = new MessageBuilder();
            Message message = factory.buildMessageWithEmptyMetadata(sample1_1);
            Message message2 = factory.buildMessageWithEmptyMetadata(sample1_2);
            inputSlot1.write(message);
            inputSlot2.write(message2);

            MergerTask merger = new MergerTask(inputSlots, outPutSlot);
            merger.doTask();

            List<Message> outputMessages = outPutSlot.getMessages();
            assertEquals(2, outputMessages.size());
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
            Logger.getLogger(MergerTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
