package cafe.models.task.transforming;

import cafe.models.message.Message;
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
        Slot inputSlot = getInputSlots().get(0);
        Slot outputSlot = getOutputSlots().get(0);

        List<Message> inputMessages = inputSlot.getMessages();
        if (inputMessages != null)
        {
            for (int i = 0; i < inputMessages.size(); i++)
            {
                Message message = new Message(inputMessages.get(i).getDocument(),
                        inputMessages.get(i).getDocumentMetaData());
                outputSlot.write(message);
            }
        }
    }
}
