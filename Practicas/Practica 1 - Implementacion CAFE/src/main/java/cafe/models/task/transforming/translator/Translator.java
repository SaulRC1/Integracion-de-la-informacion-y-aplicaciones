package cafe.models.task.transforming.translator;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.models.task.transforming.TranslatorTask;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public interface Translator 
{
    /**
     * Translates the message to the format expected by the output slot
     * 
     * @param msg The message to be translated.
     * @param outputSlot The {@link TranslatorTask}'s output slot.
     */
    public void translateMessage(Message msg, Slot outputSlot);
}
