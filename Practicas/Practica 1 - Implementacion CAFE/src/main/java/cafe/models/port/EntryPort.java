package cafe.models.port;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import java.util.UUID;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class EntryPort extends Port
{

    private final Slot slot;

    public EntryPort(Slot slot)
    {
        this.slot = slot;
    }

    public void write(Message message)
    {
        this.slot.write(message);
    }

    public Slot getSlot()
    {
        return slot;
    }
    
}
