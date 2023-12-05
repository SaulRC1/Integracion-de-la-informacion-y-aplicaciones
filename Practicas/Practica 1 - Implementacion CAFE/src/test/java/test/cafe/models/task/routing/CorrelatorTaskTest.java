package test.cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.models.task.routing.CorrelatorTask;
import cafe.models.task.routing.correlator.CafeCorrelatorCriteria;
import cafe.models.task.routing.correlator.CorrelatorCriteria;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class CorrelatorTaskTest
{

    @Test
    public void doTask()
    {
        Slot inputSlot1 = new Slot();
        Slot inputSlot2 = new Slot();
        Slot outPutSlot1 = new Slot();
        Slot outPutSlot2 = new Slot();
        List<Slot> inputSlots = new ArrayList<>();
        List<Slot> outputSlots = new ArrayList<>();
        inputSlots.add(inputSlot1);
        inputSlots.add(inputSlot2);
        outputSlots.add(outPutSlot1);
        outputSlots.add(outPutSlot2);

        CorrelatorCriteria criteria = new CafeCorrelatorCriteria();

        try
        {
            Document sample1_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1.xml");
            Document sample2_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1_1.xml");
            Document sample3_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_2.xml");
            Document sample4_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1_2.xml");
            Document sample5_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1_3.xml");

            Message message = new Message(sample2_1, sample3_1);
            Message message2 = new Message(sample5_1, sample3_1);

            Message message3 = new Message(sample4_1, sample3_1);
            Message message4 = new Message(sample5_1, sample3_1);

            inputSlot1.write(message); //Primer documento escrito coca-cola order_id = 1        
            inputSlot1.write(message3); //Segundo documento escrito cerveza order_id = 2         
            inputSlot2.write(message2); //Tercer documento escrito drink information order_id = 1           
            inputSlot2.write(message4); //Cuarto documento escrito drink information order_id = 2

            CorrelatorTask correlator = new CorrelatorTask(inputSlots, outputSlots, criteria);
            correlator.doTask();

            // Verificar que los mensajes están correlacionados correctamente
            assertTrue(outPutSlot1.getMessages().get(0).getDocumentMetaData().getElementsByTagName("order_id").item(0).getTextContent().equals("2"));
            assertTrue(outPutSlot2.getMessages().get(0).getDocumentMetaData().getElementsByTagName("order_id").item(0).getTextContent().equals("2"));

            /*int i = 0;
            for (Slot outputSlot : outputSlots)
            {
                List<Message> outputMessages = outputSlot.getMessages();

                for (Message outputMessage : outputMessages)
                {
                    Document messageBody = outputMessage.getDocument();
                    Document messageMetadata = outputMessage.getDocumentMetaData();
                    System.out.println("\n");
                    System.out.println("Mensaje " + i++ + ":");
                    XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
                    XMLDocumentParser.printDocument(messageBody.getDocumentElement());
                }
            }*/
            
            /*int i = 0;
            Slot outputSlot = outputSlots.get(0);
            List<Message> outputMessages = outputSlot.getMessages();
            for (Message outputMessage : outputMessages)
            {
                Document messageBody = outputMessage.getDocument();
                Document messageMetadata = outputMessage.getDocumentMetaData();
                System.out.println("\n");
                System.out.println("Mensaje " + i++ + ":");
                XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
                XMLDocumentParser.printDocument(messageBody.getDocumentElement());
            }*/
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(CorrelatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
