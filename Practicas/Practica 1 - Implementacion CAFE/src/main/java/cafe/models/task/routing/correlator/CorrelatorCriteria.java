package cafe.models.task.routing.correlator;

import cafe.models.slot.Slot;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public interface CorrelatorCriteria
{
    public void applyCriteria(List<Slot> inputSlots, List<Slot> outputSlots);
}
