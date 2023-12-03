package cafe.models.task;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.sequence.Sequence;
import cafe.sequence.TaskSequence;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public abstract class Task
{
    private final long id;  
    private List<Slot> inputSlots;
    private List<Slot> outputSlots;
    
    private static final String EMPTY_INPUT_SLOTS_EXCEPTION_MESSAGE = "The task's input "
            + "slots must not be empty";

    private static final String EMPTY_OUTPUT_SLOTS_EXCEPTION_MESSAGE = "The task's output "
            + "slots must not be empty";
    
    /**
     * Will buid a task with the inputs and outputs passed by parameter
     * @param inputSlots the list of inputs slot needed for the task
     * @param outputSlots the list of output slot needed for the task
     */
    public Task(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        if(inputSlots == null || inputSlots.isEmpty())
        {
            throw new IllegalArgumentException(EMPTY_INPUT_SLOTS_EXCEPTION_MESSAGE);
        }
        
        if(outputSlots == null || outputSlots.isEmpty())
        {
            throw new IllegalArgumentException(EMPTY_OUTPUT_SLOTS_EXCEPTION_MESSAGE);
        }
        
        Sequence taskSequence = TaskSequence.getTaskSequenceInstance();
        this.id = taskSequence.getNextIdentifier();
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
    }
    
    protected List<Message> readSlots() {      
        return null;        
    }
    
    protected void writeSlots(List<Message> messages) {
        
    }
    
    public abstract void doTask();

    /**
     * This method will get the list of input slots of the task
     * @return the list of input slots of the task
     */
    public List<Slot> getInputSlots()
    {
        return inputSlots;
    }

    /**
     * This method will set the list of input slots needed for the task
     * @param inputSlots the list of input slots needed for the task
     */
    public void setInputSlots(List<Slot> inputSlots)
    {
        this.inputSlots = inputSlots;
    }

    /**
     * This method will get the list of output slots of the task
     * @return the list of output slots of the task
     */
    public List<Slot> getOutputSlots()
    {
        return outputSlots;
    }

    /**
     * This method will set the list of output slots needed for the task
     * @param outputSlots the list of out`put slots needed for the task
     */
    public void setOutputSlots(List<Slot> outputSlots)
    {
        this.outputSlots = outputSlots;
    }

    /**
     * This method will get id of the task
     * @return the id of the task
     */
    public long getId()
    {
        return id;
    }    
}