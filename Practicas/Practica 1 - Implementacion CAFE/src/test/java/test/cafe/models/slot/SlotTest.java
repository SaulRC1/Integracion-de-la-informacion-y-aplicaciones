package test.cafe.models.slot;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

/**
 * Test class for {@link Slot}
 *
 * @author Saúl Rodríguez Naranjo
 */
public class SlotTest
{
    @Test
    public void test_null_message_when_reading_empty_slot_buffer()
    {
        Slot slot = new Slot();
        
        Message message = slot.read();
        
        if(message != null)
        {
            Assertions.fail();
        }
    }
    
    @Test
    public void test_message_is_removed_from_buffer_when_reading()
    {
        Slot slot = new Slot();
        
        /*Message message = new Message(document, null);
        
        slot.write(message);
        
        slot.read();
        
        Assertions.assertEquals(0, slot.getSlotBufferSize());*/
    }
}
