package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ReplicatorTask extends Task
{

    public ReplicatorTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    /**
     *
     */
    @Override
    public void doTask()
    {
        List<Slot> inputSlots = getInputSlots();
        List<Slot> outPutSlots = getOutputSlots();
        for (int i = 0; i < inputSlots.size(); i++)
        {
            Message message = inputSlots.get(i).read();
            for (int j = 0; j < outPutSlots.size(); j++)
            {
                outPutSlots.get(j).write(message);
            }
        }
    }
}
