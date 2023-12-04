package cafe.models.task.transforming.translator;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class DrinksBarmanTranslator implements Translator
{

    @Override
    public void translateMessage(Message msg, Slot outputSlot)
    {
        MessageBuilder messageBuilder = new MessageBuilder();
        
        Message translatedMessage = messageBuilder.buildEmptyMessage();
        
        Document translatedMessageBody = translatedMessage.getDocument();
        
        Document originalMessageBody = msg.getDocument();
        
        Node drinkNameNode = originalMessageBody.getElementsByTagName("name")
                .item(0);
        
        Node importedDrinkNameNode = translatedMessageBody.importNode(drinkNameNode, true);
        translatedMessageBody.appendChild(importedDrinkNameNode);
        
        Document translatedMessageMetadata = translatedMessage.getDocumentMetaData();
        
        Document originalMessageMetadata = msg.getDocumentMetaData();
        
        Node orderIdNode = originalMessageMetadata.getElementsByTagName("order_id").item(0);
        
        Node importedOrderIdNode = translatedMessageMetadata.importNode(orderIdNode, true);
        translatedMessageMetadata.getDocumentElement().appendChild(importedOrderIdNode);
        
        outputSlot.write(translatedMessage);
    }
}
