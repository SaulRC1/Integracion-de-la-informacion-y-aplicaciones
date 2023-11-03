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
    private long id;  
    private List<Slot> inputSlots;
    private List<Slot> outputSlots;

    public Task(List<Slot> inputSlots, List<Slot> outputSlots)
    {
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

    public List<Slot> getInputSlots()
    {
        return inputSlots;
    }

    public void setInputSlots(List<Slot> inputSlots)
    {
        this.inputSlots = inputSlots;
    }

    public List<Slot> getOutputSlots()
    {
        return outputSlots;
    }

    public void setOutputSlots(List<Slot> outputSlots)
    {
        this.outputSlots = outputSlots;
    }

    public long getId()
    {
        return id;
    }    
}