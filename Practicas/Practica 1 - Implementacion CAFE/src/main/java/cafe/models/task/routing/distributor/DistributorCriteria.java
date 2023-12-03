package cafe.models.task.routing.distributor;

import cafe.models.message.Message;
import cafe.models.slot.Slot;
import cafe.models.task.routing.DistributorTask;
import java.util.List;

/**
 * The criteria to follow for a {@link DistributorTask}
 * 
 * @author Saúl Rodríguez Naranjo
 */
public interface DistributorCriteria 
{
    /**
     * Applies this distributor's criteria on the {@link Message} passed as an 
     * argument, and distributes it to the corresponding output {@link Slot}.
     * 
     * @param msg The message where the criteria will be applied.
     * @param outputSlots The output slots.
     */
    public void applyCriteria(Message msg, List<Slot> outputSlots);
}
