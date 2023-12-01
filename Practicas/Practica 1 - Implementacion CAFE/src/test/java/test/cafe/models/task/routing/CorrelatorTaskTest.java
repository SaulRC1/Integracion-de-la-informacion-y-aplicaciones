package test.cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.routing.CorrelatorTask;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import cafe.xml.XPathParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

        try
        {
            Document sample1_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1.xml");
            Document sample2_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1_1.xml");
            Document sample3_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_2.xml");
            Document sample4_1 = XMLDocumentParser.parseXMLDocument("src/main/resources/CorrelatorTaskTest/sample1_1_2.xml");

            MessageBuilder factory = new MessageBuilder();
            
            Message message = new Message(sample2_1, sample1_1);
            Element orderIdElement = message.getDocumentMetaData().createElement("order_id");
            orderIdElement.appendChild(message.getDocumentMetaData().createTextNode("1"));
            message.getDocumentMetaData().getDocumentElement().appendChild(orderIdElement);
            
            String orderId = extractOrderId(message.getDocumentMetaData());
            
            Message message2 = new Message(sample2_1, sample1_1);
            /*Element orderIdElement2 = message2.getDocumentMetaData().createElement("order_id");
            orderIdElement2.appendChild(message2.getDocumentMetaData().createTextNode("1"));
            message2.getDocumentMetaData().getDocumentElement().appendChild(orderIdElement2);*/
            
            //Message message = factory.buildMessageWithEmptyMetadata(sample1_1);
            //Message message2 = factory.buildMessageWithEmptyMetadata(sample2_1);
            //Message message3 = factory.buildMessageWithEmptyMetadata(sample3_1);
            //Message message4 = factory.buildMessageWithEmptyMetadata(sample4_1);
            
            Message message3 = new Message(sample4_1, sample3_1);
            /*Element orderIdElement3 = message3.getDocumentMetaData().createElement("order_id");
            orderIdElement3.appendChild(message3.getDocumentMetaData().createTextNode("2"));
            message3.getDocumentMetaData().getDocumentElement().appendChild(orderIdElement3);*/
            
            Message message4 = new Message(sample4_1, sample3_1);
            /*Element orderIdElement4 = message4.getDocumentMetaData().createElement("order_id");
            orderIdElement4.appendChild(message4.getDocumentMetaData().createTextNode("2"));
            message4.getDocumentMetaData().getDocumentElement().appendChild(orderIdElement4);*/
            
            inputSlot1.write(message);
            inputSlot1.write(message2);
            inputSlot2.write(message3);
            inputSlot2.write(message4);

            CorrelatorTask correlator = new CorrelatorTask(inputSlots, outputSlots, "/metadata/order_id/text()");
            correlator.doTask();

            int i = 0;
            // Verificar el resultado
            String expectedMetaData = "1";
            NodeList childNodes = outPutSlot1.getMessages().get(0).getDocumentMetaData().getElementsByTagName("order_id");

            if (childNodes.getLength() > 0)
            {
                Node orderIdNode = childNodes.item(0);
                String actualMetaData = orderIdNode.getTextContent();

                assertEquals(expectedMetaData, actualMetaData);
            } else
            {
                // Manejar el caso en que no se encuentra ningún nodo "order_id"
                fail("No se encontró el nodo 'order_id' en el documentMetaData.");
            }
            for (Slot outputSlot : outputSlots)
            {
                List<Message> outputMessages = outputSlot.getMessages();
                //assertEquals(2, outputMessages.size());

                for (Message outputMessage : outputMessages)
                {
                    Document messageBody = outputMessage.getDocument();
                    Document messageMetadata = outputMessage.getDocumentMetaData();
                    System.out.println("\n");
                    System.out.println("Mensaje " + i++ + ":");
                    XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
                    XMLDocumentParser.printDocument(messageBody.getDocumentElement());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException | XPathExpressionException ex)
        {
            Logger.getLogger(CorrelatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String extractOrderId(Document documentMetaData) throws XPathExpressionException
    {
        // Aquí asumo que el order_id es un elemento directo del documentMetaData
        String xPathExpression = "/metadata/order_id/text()";
        XPathParser xpath = new XPathParser();
        return (String) xpath.executeXPathExpression(xPathExpression, documentMetaData, XPathConstants.STRING);
    }
}