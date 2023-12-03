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

    public ExitPort(Slot slot)
    {
        this.slot = slot;
    }

    public Message read(Message message)
    {
        return null;
    }

    public Slot getSlot()
    {
        return slot;
    }

    public void setSlot(Slot slot)
    {
        this.slot = slot;
    }
}
