package cafe.models.task.routing.correlator;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class CafeCorrelatorCriteria implements CorrelatorCriteria
{
    private int turnoOutputslot = 0;
    
    @Override
    public void applyCriteria(Message msg, List<Slot> intputSlots, List<Slot> outputSlots)
    {
        String idOrder = msg.getDocumentMetaData().getElementsByTagName("order_id").item(0).getTextContent();

        for (Slot intputSlot : intputSlots)
        {
            List<Message> messages = intputSlot.getMessages();
            for (Message message : messages)
            {
                if (idOrder.equals(message.getDocumentMetaData().getElementsByTagName("order_id").item(0).getTextContent()))
                {
                    writeCorrelatedMessages(message, outputSlots);
                }
            }
        }
        turnoOutputslot = (turnoOutputslot + 1) % outputSlots.size();
    }

    private void writeCorrelatedMessages(Message message, List<Slot> outputSlots)
    {       
        if (turnoOutputslot == 0)
        {
            outputSlots.get(0).write(message);
        } else
        {
            outputSlots.get(1).write(message);
        }
    }
}