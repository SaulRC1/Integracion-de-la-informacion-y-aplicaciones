package test.cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.models.task.transforming.SplitterTask;
import static cafe.models.task.transforming.SplitterTask.SPLITTER_METADATA_OPEN_TAG;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
            Document sample1_2 = XMLDocumentParser.parseXMLDocument("src/main/resources/SplitterTaskTest/sample1_2.xml");

            Document sample2_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/SplitterTaskTest/sample1_1.xml");
            Document sample2_2 = XMLDocumentParser.parseXMLDocument("src/main/resources/SplitterTaskTest/sample1_2.xml");

            Message message = new Message(sample1_1, sample1_2);
            Message message2 = new Message(sample2_1, sample2_2);
            inputSlot.write(message);
            inputSlot.write(message2);

            // Crear una instancia de SplitterTask con una expresión XPath simple
            SplitterTask splitterTask = new SplitterTask(inputSlot, outputSlot, "//drink");

            // Ejecutar la tarea
            splitterTask.doTask();

            // Verificar el resultado
            //List<Message> outputMessages = outputSlot.getMessages();
            //assertEquals(2, outputMessages.size()); 
            for (Message outputMessage : outputSlot.getMessages())
            {
                Document metaData = outputMessage.getDocumentMetaData();

                // Verificar que la etiqueta <splitterID>1</splitterID> está presente en el DocumentMetaData
                NodeList splitterIDList = metaData.getElementsByTagName(SPLITTER_METADATA_OPEN_TAG);
                assertEquals(1, splitterIDList.getLength());

                Node splitterIDNode = splitterIDList.item(0);
                assertEquals("1", splitterIDNode.getTextContent());
            }
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(SplitterTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
