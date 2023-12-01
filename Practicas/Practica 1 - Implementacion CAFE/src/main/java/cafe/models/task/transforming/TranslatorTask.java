package cafe.models.task.transforming;

import cafe.models.slot.Slot;
import cafe.models.task.Task;
import java.util.List;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public class TranslatorTask extends Task
{

    public TranslatorTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {

    }
}
