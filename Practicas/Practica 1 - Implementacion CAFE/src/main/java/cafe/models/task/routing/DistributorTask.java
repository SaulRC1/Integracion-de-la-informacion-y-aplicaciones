package cafe.models.task.routing;

import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public class DistributorTask extends Task
{

    public DistributorTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {

    }
}
