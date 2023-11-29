package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.xml.XPathParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class CorrelatorTask extends Task
{
    public CorrelatorTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {
        List<Slot> inputSlots = getInputSlots();
        List<Slot> outputSlots = getOutputSlots();

        for (Slot inputSlot : inputSlots)
        {
            List<Message> messages = inputSlot.getMessages();
            for (Message message : messages)
            {
                try
                {
                    // Obtener el order_id del documentMetaData
                    String orderId = extractOrderId(message.getDocumentMetaData());

                    // Verificar si hay otros mensajes con el mismo order_id en los demás inputSlots
                    boolean matchFound = checkForMatchingOrderId(orderId, inputSlots);

                    if (matchFound)
                    {
                        // Si hay coincidencia, escribir el mensaje en los outputSlots
                        for (Slot outputSlot : outputSlots)
                        {
                            outputSlot.write(message);
                        }
                    }
                } catch (XPathExpressionException | ParserConfigurationException ex)
                {
                    Logger.getLogger(CorrelatorTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private String extractOrderId(Document documentMetaData) throws XPathExpressionException
    {
        // Aquí asumo que el order_id es un elemento directo del documentMetaData
        String xPathExpression = "/metadata/order_id/text()";
        XPathParser xpath = new XPathParser();
        return (String) xpath.executeXPathExpression(xPathExpression, documentMetaData, XPathConstants.STRING);
    }

    private boolean checkForMatchingOrderId(String orderId, List<Slot> inputSlots)
            throws XPathExpressionException, ParserConfigurationException
    {
        for (Slot inputSlot : inputSlots)
        {
            if (!inputSlot.equals(this) && containsOrderId(orderId, inputSlot.getMessages()))
            {
                return true;
            }
        }
        return false;
    }

    private boolean containsOrderId(String orderId, List<Message> messages)
            throws XPathExpressionException, ParserConfigurationException
    {
        for (Message message : messages)
        {
            String otherOrderId = extractOrderId(message.getDocumentMetaData());
            if (orderId.equals(otherOrderId))
            {
                return true;
            }
        }
        return false;
    }

    private void OrderMessagesBy(String xPathExpression, List<Message> messages, List<Slot> outputSlots) throws XPathExpressionException, ParserConfigurationException
    {
        XPathParser xpath = new XPathParser();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document documentOrdered = documentBuilder.newDocument();

        for (Message message : messages)
        {
            Object result = xpath.executeXPathExpression(xPathExpression, message.getDocument(), XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;

            for (int j = 0; j < nodeList.getLength(); j++)
            {
                Node selectedNode = nodeList.item(j);
                Node importedNode = documentOrdered.importNode(selectedNode, true);
                documentOrdered.appendChild(importedNode);
                Message outputMessage = new Message(documentOrdered, message.getDocumentMetaData());
                for (Slot outputSlot : outputSlots)
                {
                    outputSlot.write(outputMessage);
                }
            }
        }
    }
}
