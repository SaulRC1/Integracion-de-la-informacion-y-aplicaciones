package cafe.models.slot;

import cafe.models.message.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * A Slot acts as a message buffer for communication between tasks or between a 
 * task and a {@link Port}
 * 
 * @author Juan Alberto Dominguez Vazquez
 */
public class Slot
{

    private final List<Message> messages;

    /**
     * Will build a Slot.
     */
    public Slot()
    {
        this.messages = new ArrayList<>();
    }

    /**
     * Reads a message from this Slot.
     *
     * <p>
     * This method will read the last message stored in this slot and then
     * delete it from the buffer.
     * </p>
     *
     * <p>
     * If there are no messages present in the buffer and a read attempt is made
     * then this method will return null;
     * </p>
     *
     * @return The last message stored in this slot.
     */
    public Message read()
    {
        if (messages.isEmpty())
        {
            return null;
        }

        Message readMessage = this.messages.get(messages.size() - 1);

        this.messages.remove(messages.size() - 1);

        return readMessage;
    }

    /**
     * Reads a message from this Slot.
     *
     * <p>
     * This method will read the last message stored in this slot without
     * deleting it from the buffer.
     * </p>
     *
     * <p>
     * If there are no messages present in the buffer and a read attempt is made
     * then this method will return null;
     * </p>
     *
     * @return The last message stored in this slot.
     */
    public Message readWithoutRemoval()
    {
        if (messages.isEmpty())
        {
            return null;
        }

        return this.messages.get(messages.size() - 1);
    }

    /**
     * Will write a message into the buffer.
     * 
     * <p>
     * If the message is null, no write operation will be done.
     * </p>
     * 
     * @param message The message to be written.
     */
    public void write(Message message)
    {
        if(message != null)
        {
            this.messages.add(message);
        }
    }

    /**
     * Returns all the messages present in this {@link Slot}.
     * 
     * @return ll the messages present in this {@link Slot}.
     */
    public List<Message> getMessages()
    {
        return messages;
    }

    /**
     * Returns the number of messages present in this {@link Slot}'s buffer.
     * 
     * @return The buffer's size.
     */
    public int getSlotBufferSize()
    {
        return messages.size();
    }
}
