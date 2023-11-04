package cafe.models.task.modifying;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class CorrelationIdSetter extends Task
{   
    private long ID_SEQUENCE_NUMBER = 0;
    /**
     * 
     * @param inputSlot
     * @param outputSlot
     */
    public CorrelationIdSetter(Slot inputSlot, Slot outputSlot)
    {
        super(List.of(inputSlot), List.of(outputSlot));
    }

    /**
     * Genera un ID de correlación, 
     * almacena dicho ID en la cabecera del mensaje
     * y por último escribe el mensaje en el slot de salida
     */
    @Override
    public void doTask()
    {
        Slot inputSlot = getInputSlots().get(0);
        Slot outputSlot = getOutputSlots().get(0);

        Message message = inputSlot.read();
        if (message != null) {
            // Generar un ID de correlación
            String correlationId = String.valueOf(generateCorrelationId());
            message.getDocumentMetaData().createAttribute(correlationId);
            outputSlot.write(message);
        }
    }

    private long generateCorrelationId()
    {
        return ID_SEQUENCE_NUMBER + 1;      
    }
}