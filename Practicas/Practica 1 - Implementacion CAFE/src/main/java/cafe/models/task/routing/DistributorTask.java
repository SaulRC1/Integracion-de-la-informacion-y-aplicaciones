package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.models.task.routing.distributor.DistributorCriteria;
import java.util.List;

/**
 * Distributes input messages towards one or more output {@link Slot} following
 * a criteria.
 * 
 * @author Saul Rodriguez Naranjo
 */
public class DistributorTask extends Task
{

    private final static int MAX_NUMBER_OF_INPUT_SLOTS = 1;
    
    private final static String MAXIMUM_NUMBER_OF_INPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE 
            = "Maximum number of input slots exceeded, there must only be one.";
    
    private final static String EMPTY_OR_NULL_CRITERIA_EXCEPTION = "The criteria"
            + "for distributing messages must not be null.";
    
    private DistributorCriteria criteria;
    
    private Slot inputSlot;
    
    /**
     * Builds a distributor task.
     * 
     * <p>
     * There must only be one input slot, and any number of output slots for
     * this task.
     * </p>
     * 
     * @param inputSlots The input slots, there must only be one.
     * @param outputSlots The output slots
     * @param criteria The criteria to follow for this distributor
     */
    public DistributorTask(List<Slot> inputSlots, List<Slot> outputSlots, 
            DistributorCriteria criteria)
    {
        super(inputSlots, outputSlots);
        
        if (inputSlots.size() > MAX_NUMBER_OF_INPUT_SLOTS)
        {
            throw new IllegalArgumentException(MAXIMUM_NUMBER_OF_INPUT_SLOTS_EXCEEDED_EXCEPTION_MESSAGE);
        }
        
        if(criteria == null)
        {
            throw new IllegalArgumentException(EMPTY_OR_NULL_CRITERIA_EXCEPTION);
        }
        
        this.criteria = criteria;
        this.inputSlot = inputSlots.get(0);
    }

    @Override
    public void doTask()
    {
        Message msg;
        
        while((msg = inputSlot.read()) != null)
        {
            criteria.applyCriteria(msg, this.getOutputSlots());
        }
    }
}
