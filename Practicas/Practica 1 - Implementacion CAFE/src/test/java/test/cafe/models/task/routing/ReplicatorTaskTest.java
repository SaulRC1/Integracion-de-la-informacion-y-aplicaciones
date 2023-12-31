package test.cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.models.task.routing.ReplicatorTask;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ReplicatorTaskTest
{
    public ReplicatorTaskTest()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
    }

    @AfterAll
    public static void tearDownClass()
    {
    }

    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test of doTask method, of class ReplicatorTask.
     */
    @Test
    public void testDoTask()
    {
        Slot inputSlot = new Slot();
        Slot outPutSlot1 = new Slot();
        Slot outPutSlot2 = new Slot();

        List<Slot> outPutSlots = new ArrayList();
        outPutSlots.add(outPutSlot1);
        outPutSlots.add(outPutSlot2);

        List<Message> messages = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory
                = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("root"); // Crea un elemento raíz 
            rootElement.setTextContent("Hola");
            document.appendChild(rootElement); // Agrega el elemento raíz al documento
            
            Document documentMetaData = documentBuilder.newDocument();
            Element rootElement2 = documentMetaData.createElement("root"); // Crea un elemento raíz 
            rootElement2.setTextContent("metadatos");
            documentMetaData.appendChild(rootElement2); // Agrega el elemento raíz al documento

            Message message1 = new Message(document, documentMetaData);
            Message message2 = new Message(document, documentMetaData);
            messages.add(message1);
            messages.add(message2);

            inputSlot.write(message1);
            inputSlot.write(message2);

            ReplicatorTask replicator = new ReplicatorTask(inputSlot, outPutSlots);
            replicator.doTask();

            Assertions.assertEquals(2, replicator.getOutputSlots().get(0).getMessages().size());
            Assertions.assertEquals(2, replicator.getOutputSlots().get(1).getMessages().size());

        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(ReplicatorTaskTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}