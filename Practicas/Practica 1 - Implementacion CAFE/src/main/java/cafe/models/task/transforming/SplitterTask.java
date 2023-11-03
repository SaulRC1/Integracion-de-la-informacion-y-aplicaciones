package cafe.models.task.transforming;

import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class SplitterTask extends Task
{

    public SplitterTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {

    }
}
