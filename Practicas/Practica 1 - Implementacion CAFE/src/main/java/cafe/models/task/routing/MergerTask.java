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
    /**
     * Will build a Merger
     * @param inputSlots Numbers of input slots that the task in going to have
     * @param outputSlot Numbers of output slots that the task in going to have
     */
    public MergerTask(List<Slot> inputSlots, Slot outputSlot)
    {
        super(inputSlots, List.of(outputSlot));
    }

    /**
     * <p>
     * This method will join the messages stored in the differents inputs slots and then
     * put it all on the output slot.
     * </p>
     */
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