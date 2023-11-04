package cafe.models.task.modifying;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ContextEnricherTask extends Task
{
    private Slot contextSlot;

    /**
     *
     * @param inputSlot
     * @param outputSlot
     * @param contextSlot
     */
    public ContextEnricherTask(Slot inputSlot, Slot outputSlot, Slot contextSlot)
    {
        super(List.of(inputSlot, contextSlot), List.of(outputSlot));
        this.contextSlot = contextSlot;
    }

    /**
     *
     */
    @Override
    public void doTask()
    {
        List<Slot> inputSlots = getInputSlots();
        Slot outputSlot = getOutputSlots().get(0);

        // Leer contexto
        String contexto = readContext();

        // Leer mensajes de las entradas y enriquecer el contenido con el contexto
        for (Slot inputSlot : inputSlots)
        {
            Message message = inputSlot.read();
            if (message != null)
            {
                // Añadir contenido del contexto al cuerpo del mensaje
                String contenido = message.getDocument().getTextContent();
                contenido += contexto;
                message.getDocument().setTextContent(contenido);
                outputSlot.write(message);
            }
        }
    }

    private String readContext()
    {
        // Leer contexto del slot de contexto
        Message contextMessage = contextSlot.read();
        if (contextMessage != null)
        {
            return contextMessage.getDocument().getTextContent();
        }
        return ""; // Valor predeterminado si no hay contexto disponible
    }

    /**
     *
     * @return
     */
    public Slot getContextSlot()
    {
        return contextSlot;
    }

    /**
     *
     * @param contextSlot
     */
    public void setContextSlot(Slot contextSlot)
    {
        this.contextSlot = contextSlot;
    }  
}