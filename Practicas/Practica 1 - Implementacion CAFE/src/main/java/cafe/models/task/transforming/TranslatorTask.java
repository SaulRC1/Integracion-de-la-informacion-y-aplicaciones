package cafe.models.task.transforming;

import cafe.models.slot.Slot;
import cafe.models.task.Task;
import java.util.List;
import cafe.models.message.Message;
import cafe.models.task.transforming.translator.Translator;

/**
 * Transforms a {@link Message}'s body into another schema.
 * 
 * @author Saul Rodriguez Naranjo
 */
public class TranslatorTask extends Task
{
    private Slot inputSlot;
    private Slot outputSlot;
    
    private Translator translator;
    
    private static final String NULL_TRANSLATOR_EXCEPTION_MESSAGE = 
            "Translator must not be null";

    /**
     * Will build a translator task.
     * 
     * @param inputSlot The input slot.
     * @param outputSlot The output slot.
     * @param translator The translator to be used with this task.
     */
    public TranslatorTask(Slot inputSlot, Slot outputSlot, Translator translator)
    {
        super(List.of(inputSlot), List.of(outputSlot));
        
        if(translator == null)
        {
            throw new IllegalArgumentException(NULL_TRANSLATOR_EXCEPTION_MESSAGE);
        }
        
        this.translator = translator;
        this.inputSlot = inputSlot;
        this.outputSlot = outputSlot;
    }

    @Override
    public void doTask()
    {
        Message msg;
        
        while((msg = inputSlot.read()) != null)
        {
            translator.translateMessage(msg, outputSlot);
        }
    }
}
