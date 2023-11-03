package cafe.models.task;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public abstract class Task
{
    private UUID id;  
    private List<Slot> inputSlots;
    private List<Slot> outputSlots;

    public Task(UUID id, List<Slot> inputSlots, List<Slot> outputSlots)
    {
        this.id = id;
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
    }
    
    protected List<Message> readSlots() {      
        return null;        
    }
    
    protected void writeSlots(List<Message> messages) {
        
    }
    
    public void doTask() {
        
    }

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

    public UUID getId()
    {
        return id;
    }    
}