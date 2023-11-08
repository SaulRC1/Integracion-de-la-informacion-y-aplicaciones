package cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

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
        int numberOfSplitters = getNumberOfSplitters(inputSlot.getMessages());
        
        for (int i = 0; i < numberOfSplitters; i++)
        {
            
        }
    }

    private int getNumberOfSplitters(List<Message> messages)
    {
        return 0;
    }
}
