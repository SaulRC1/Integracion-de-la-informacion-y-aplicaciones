package cafe.models.task.routing;

import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class MergerTask extends Task
{   
    public MergerTask(UUID id, List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(id, inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {
        
    }   
}