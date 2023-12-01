package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.xml.XPathParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class CorrelatorTask extends Task
{
    private String xPathExpression;

    /**
     * Will build a Correlator
     * @param inputSlots Numbers of input slots that the task in going to have
     * @param outputSlots Numbers of output slots that the task in going to have
     * @param xPathExpression The xPath expression needed to know for correlate the messages
     */
    public CorrelatorTask(List<Slot> inputSlots, List<Slot> outputSlots, String xPathExpression)
    {
        super(inputSlots, outputSlots);
        this.xPathExpression = xPathExpression;
    }

    /**
     * <p>
     * This method will order the messages located on the input slots and will correlate
     * them with the xPath expression that we want and write them on the output slots
     * </p>
     */
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
                    XPathParser xpath = new XPathParser();
                    String orderId = (String) xpath.executeXPathExpression(xPathExpression, message.getDocumentMetaData(), XPathConstants.STRING);

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
            XPathParser xpath = new XPathParser();
            String otherOrderId = (String) xpath.executeXPathExpression(xPathExpression, message.getDocumentMetaData(), XPathConstants.STRING);
            //String otherOrderId = extractOrderId(message.getDocumentMetaData());
            if (orderId.equals(otherOrderId))
            {
                return true;
            }
        }
        return false;
    }
}