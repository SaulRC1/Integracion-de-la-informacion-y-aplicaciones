package cafe.models.port;

import cafe.models.message.Message;
import cafe.models.slot.Slot;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public class EntryPort extends Port
{
    private final Slot slot;

    /**
     * Will build a entry port with one slot
     * @param slot the slot needed for the port
     */
    public EntryPort(Slot slot)
    {
        super();
        this.slot = slot;
    }

    /**
     * This method will write a message in the slot
     * @param message The message that will be written
     */
    public void write(Message message)
    {
        this.slot.write(message);
    }

    /**
     * This method will get the slot of the port
     * @return the slot of the port
     */
    public Slot getSlot()
    {
        return slot;
    }    
}