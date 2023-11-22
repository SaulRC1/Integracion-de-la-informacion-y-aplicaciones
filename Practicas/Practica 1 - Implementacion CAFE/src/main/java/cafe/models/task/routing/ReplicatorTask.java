package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ReplicatorTask extends Task
{

    public ReplicatorTask(Slot inputSlot, List<Slot> outputSlots)
    {
        super(List.of(inputSlot), outputSlots);
    }

    /**
     *
     */
    @Override
    public void doTask()
    {
        Slot inputSlot = getInputSlots().get(0);
        List<Slot> outPutSlots = getOutputSlots();
        List<Message> inputMessages = new ArrayList<>();

        int inputSlotBufferSize = inputSlot.getSlotBufferSize();

        for (int i = 0; i < inputSlotBufferSize; i++)
        {
            inputMessages.add(inputSlot.read());
        }

        for (Slot outPutSlot : outPutSlots)
        {
            for (Message message : inputMessages)
            {
                try
                {
                    Message clonedMessage = (Message) message.clone();
                    outPutSlot.write(clonedMessage);
                } catch (CloneNotSupportedException ex)
                {
                    Logger.getLogger(ReplicatorTask.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }
}
