package cafe.models.port;

import cafe.models.message.Message;
import cafe.models.slot.Slot;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public class EntryExitPort extends Port
{
    private Slot outputSlot;
    private Slot inputSlot;

    public EntryExitPort(Slot outputSlot, Slot inputSlot)
    {
        this.outputSlot = outputSlot;
        this.inputSlot = inputSlot;
    }

    public void writeIntoInputSlot(Message message)
    {
        this.inputSlot.write(message);
    }
    
    public void writeIntoOutputSlot(Message message)
    {
        this.outputSlot.write(message);
    }

    public Message readFromOutputSlot()
    {
        return outputSlot.read();
    }
    
    public Message readFromInputSlot()
    {
        return inputSlot.read();
    }

    public Slot getOutputSlot()
    {
        return outputSlot;
    }

    public void setOutputSlot(Slot outputSlot)
    {
        this.outputSlot = outputSlot;
    }

    public Slot getInputSlot()
    {
        return inputSlot;
    }

    public void setInputSlot(Slot inputSlot)
    {
        this.inputSlot = inputSlot;
    }
    
}
