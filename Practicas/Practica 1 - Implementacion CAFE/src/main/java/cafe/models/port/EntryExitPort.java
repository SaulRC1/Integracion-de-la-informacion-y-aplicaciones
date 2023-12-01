package cafe.models.port;

import cafe.models.message.Message;
import cafe.models.slot.Slot;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public class EntryExitPort extends Port
{

    private Slot readSlot;
    private Slot writeSlot;

    public EntryExitPort()
    {

    }

    public void write(Message message)
    {

    }

    public Message read(Message message)
    {
        return null;
    }

    public Slot getReadSlot()
    {
        return readSlot;
    }

    public void setReadSlot(Slot readSlot)
    {
        this.readSlot = readSlot;
    }

    public Slot getWriteSlot()
    {
        return writeSlot;
    }

    public void setWriteSlot(Slot writeSlot)
    {
        this.writeSlot = writeSlot;
    }
}
