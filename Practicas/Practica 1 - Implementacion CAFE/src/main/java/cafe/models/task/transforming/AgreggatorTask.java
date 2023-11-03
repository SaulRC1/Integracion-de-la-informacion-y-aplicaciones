package cafe.models.task.transforming;

import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class AgreggatorTask extends Task
{    
    public AgreggatorTask(UUID id, List<Slot> inputSlots, List<Slot> outputSlots)
    {
        super(id, inputSlots, outputSlots);
    }

    @Override
    public void doTask()
    {
        super.doTask(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }   
}