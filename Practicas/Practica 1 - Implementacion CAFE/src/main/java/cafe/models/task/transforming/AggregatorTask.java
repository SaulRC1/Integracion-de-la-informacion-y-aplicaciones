package cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.xml.XPathParser;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class AggregatorTask extends Task
{

    private static final int MAX_NUMBER_OF_INPUT_SLOTS = 1;
    private static final int MAX_NUMBER_OF_OUTPUT_SLOTS = 1;

    private static final String MAXIMUM_NUMBER_OF_INPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE
            = "Maximum number of input slots exceeded, there must only be one.";

    private static final String MAXIMUM_NUMBER_OF_OUTPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE
            = "Maximum number of output slots exceeded, there must only be one.";

    private Slot inputSlot;
    private Slot outputSlot;

    public AggregatorTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);

        if (inputSlots.size() > MAX_NUMBER_OF_INPUT_SLOTS)
        {
            throw new IllegalArgumentException(MAXIMUM_NUMBER_OF_INPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE);
        }

        if (outputSlots.size() > MAX_NUMBER_OF_OUTPUT_SLOTS)
        {
            throw new IllegalArgumentException(MAXIMUM_NUMBER_OF_OUTPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE);
        }

        inputSlot = this.getInputSlots().get(0);
        outputSlot = this.getOutputSlots().get(0);
    }

    @Override
    public void doTask()
    {
        //First get from how many splitters our messages come from
        Set<Long> allSplitters = getNumberOfSplitters(inputSlot.getMessages());

        Iterator<Long> allSplittersIterator = allSplitters.iterator();

        while (allSplittersIterator.hasNext())
        {
            System.out.println("SplitterId from Aggregator: " + allSplittersIterator.next());
        }

        /*Message msg;
        
        while((msg = inputSlot.read()) != null)
        {
            
        }*/
    }

    /**
     * Gets how many different splitters do the messages come from.
     *
     * @param messages The list of messages.
     * @return The different splitters where the messages come from.
     */
    private Set<Long> getNumberOfSplitters(List<Message> messages)
    {
        Set<Long> splittersIds = new HashSet<>();

        for (Message message : messages)
        {
            Document messageMetadata = message.getDocumentMetaData();

            XPathParser xpathParser = new XPathParser();

            try
            {
                Node splitterIdNode = (Node) xpathParser.executeXPathExpression(
                        "/metadata/splitterId", messageMetadata,
                        XPathConstants.NODE);

                long splitterId = Long.parseLong(splitterIdNode.getChildNodes().item(0).getNodeValue());

                splittersIds.add(splitterId);

            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(AggregatorTask.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return splittersIds;
    }

    /**
     * Gets the splitter id from a message's metadata.
     * 
     * <p>
     * In case the retrieval of the id fails, a -1 will be returned.
     * </p>
     *
     * @param msg The message.
     * @return The message's splitter id.
     */
    private long getSplitterId(Message msg)
    {
        try
        {
            Document messageMetadata = msg.getDocumentMetaData();
            
            XPathParser xpathParser = new XPathParser();
            
            Node splitterIdNode = (Node) xpathParser.executeXPathExpression(
                    "/metadata/splitterId", messageMetadata,
                    XPathConstants.NODE);
            
            long splitterId = Long.parseLong(splitterIdNode.getChildNodes().item(0).getNodeValue());
            
            return splitterId;
            
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(AggregatorTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
}
