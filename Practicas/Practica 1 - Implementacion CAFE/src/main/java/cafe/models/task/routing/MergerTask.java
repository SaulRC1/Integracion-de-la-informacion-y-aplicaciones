package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class MergerTask extends Task
{

    public MergerTask(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {
        List<Slot> inputSlots = getInputSlots();
        Slot outPutSlot = getOutputSlots().get(0);
        
        for (int i = 0; i < inputSlots.size(); i++) {
            Message message = inputSlots.get(i).read();
            if (message != null) {
                outPutSlot.write(message);
            }
        }
    }
}
