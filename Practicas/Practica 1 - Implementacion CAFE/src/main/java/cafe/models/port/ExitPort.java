package cafe.models.port;

import cafe.models.message.Message;
import cafe.models.slot.Slot;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public class ExitPort extends Port
{
    private Slot slot;

    /**
     * Will build an exit port with one slot
     * @param slot the slot needed for the port
     */
    public ExitPort(Slot slot)
    {
        super();
        this.slot = slot;
    }

    /**
     * This method will read a message from the slot
     * @return the message stored in the slot
     */
    public Message read()
    {
        Message message = this.slot.read();
        return message;
    }

    /**
     * This method will get the slot of the port
     * @return the slot of the port
     */
    public Slot getSlot()
    {
        return slot;
    }

    /**
     * This method will set the slot of the port
     * @param slot the slot needed for the port
     */
    public void setSlot(Slot slot)
    {
        this.slot = slot;
    }
}