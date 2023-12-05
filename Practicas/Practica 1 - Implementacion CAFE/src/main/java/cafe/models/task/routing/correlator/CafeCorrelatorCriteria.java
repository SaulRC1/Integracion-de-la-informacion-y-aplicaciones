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
    @Override
    public void applyCriteria(List<Slot> inputSlots, List<Slot> outputSlots)
    {
        Slot replicatorOutputSlot = inputSlots.get(0);
        Slot correlatorOutputSlot_1 = outputSlots.get(0);
        Slot correlatorOutputSlot_2 = outputSlots.get(1);

        Message messageFromReplicator;

        while ((messageFromReplicator = replicatorOutputSlot.read()) != null)
        {
            correlatorOutputSlot_1.write(messageFromReplicator);
        }

        Slot barmanOutputSlot = inputSlots.get(1);

        List<Message> correlatorMessages = correlatorOutputSlot_1.getMessages();
        List<Message> barmanMessagesOutputSlot = barmanOutputSlot.getMessages();

        for (Message correlatorMessage : correlatorMessages)
        {
            String orderId = correlatorMessage.getDocumentMetaData().getElementsByTagName("order_id").item(0).getTextContent();
            String drinkName = correlatorMessage.getDocument().getElementsByTagName("name").item(0).getTextContent();

            Message correspondingMessage = null;
            for (Message message : barmanMessagesOutputSlot)
            {
                String orderIdBarman = message.getDocumentMetaData().getElementsByTagName("order_id").item(0).getTextContent();
                String drinkNameBarman = message.getDocumentMetaData().getElementsByTagName("name").item(0).getTextContent();
                if (orderId.equals(orderIdBarman) && drinkName.equals(drinkNameBarman))
                {
                    correspondingMessage = message;
                    break;
                }
            }
            correlatorOutputSlot_2.write(correspondingMessage);
        }
    }
}