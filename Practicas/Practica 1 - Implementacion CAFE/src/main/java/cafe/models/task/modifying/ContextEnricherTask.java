package cafe.models.task.modifying;

import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ContextEnricherTask extends Task
{

    public ContextEnricherTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {

    }

}
