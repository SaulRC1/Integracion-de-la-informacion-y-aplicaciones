package test.cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.transforming.SplitterTask;
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
public class SplitterTaskTest
{
    @Test
    public void test()
    {
        try
        {
            Slot inputSlot = new Slot();
            Slot outputSlot = new Slot();

            Document sample1_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/SplitterTaskTest/sample1_1.xml");
            Document sample2_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/SplitterTaskTest/sample1_1.xml");

            MessageBuilder factory = new MessageBuilder();
            Message message = factory.buildMessageWithEmptyMetadata(sample1_1);
            Message message2 = factory.buildMessageWithEmptyMetadata(sample2_1);        
            inputSlot.write(message);
            inputSlot.write(message2);

            // Crear una instancia de SplitterTask con una expresión XPath simple
            SplitterTask splitterTask = new SplitterTask(inputSlot, outputSlot, "/cafe_order/drinks/drink");

            // Ejecutar la tarea
            splitterTask.doTask();

            // Verificar el resultado
            List<Message> outputMessages = outputSlot.getMessages();
            assertEquals(4, outputMessages.size()); 
            int i = 0;
            for (Message outputMessage : outputMessages)
            {            
                Document messageBody = outputMessage.getDocument();
                Document messageMetadata = outputMessage.getDocumentMetaData();
                System.out.println("Mensaje " + i++ + ":" );
                XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
                XMLDocumentParser.printDocument(messageBody.getDocumentElement());           
            }
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(SplitterTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}