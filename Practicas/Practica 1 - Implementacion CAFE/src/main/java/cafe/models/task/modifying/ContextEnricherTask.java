package cafe.models.task.modifying;

import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ContextEnricherTask extends Task
{  
    public ContextEnricherTask(UUID id, List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(id, inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {
        
    }  

    @Override
    public UUID getId()
    {
        return super.getId(); 
    }
    
}