package cafe.models.slot;

import cafe.models.message.Message;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class Slot
{
    private List<Message> messages;

    public Slot(List<Message> messages)
    {
        this.messages = messages;
    }
    
    public Message read() {
        return null;        
    }
    
    public void write(Message message) {
        
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }
}