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

    public EntryExitPort(Slot readSlot, Slot writeSlot)
    {
        this.readSlot = readSlot;
        this.writeSlot = writeSlot;
    }

    public void write(Message message)
    {
        this.writeSlot.write(message);
    }

    public Message read()
    {
        return readSlot.read();
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
