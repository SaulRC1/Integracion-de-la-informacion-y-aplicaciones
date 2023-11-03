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

    private Slot slot;

    public EntryPort()
    {

    }

    public void write(Message message)
    {

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
