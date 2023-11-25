package cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.xml.XPathParser;
import java.util.HashSet;
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
public class AgreggatorTask extends Task
{
    private static final int MAX_NUMBER_OF_INPUT_SLOTS = 1;
    private static final int MAX_NUMBER_OF_OUTPUT_SLOTS = 1;
    
    private static final String MAXIMUM_NUMBER_OF_INPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE 
            = "Maximum number of input slots exceeded, there must only be one.";
    
    private static final String MAXIMUM_NUMBER_OF_OUTPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE 
            = "Maximum number of output slots exceeded, there must only be one.";
    
    private Slot inputSlot;
    private Slot outputSlot;

    public AgreggatorTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
        
        if(inputSlots.size() > MAX_NUMBER_OF_INPUT_SLOTS)
        {
            throw new IllegalArgumentException(MAXIMUM_NUMBER_OF_INPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE);
        }
        
        if(outputSlots.size() > MAX_NUMBER_OF_OUTPUT_SLOTS)
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
        
        /*for (int i = 0; i < numberOfSplitters; i++)
        {
            
        }*/
    }

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
                
                //splitterIdNode.get
                
                //splittersIds.add()
                        
            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(AgreggatorTask.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
