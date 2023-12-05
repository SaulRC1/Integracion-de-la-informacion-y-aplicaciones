package cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.models.task.routing.correlator.CorrelatorCriteria;
import java.util.List;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class CorrelatorTask extends Task
{
    private CorrelatorCriteria criteria;

    /**
     * Will build a Correlator
     *
     * @param inputSlots Numbers of input slots that the task in going to have
     * @param outputSlots Numbers of output slots that the task in going to have
     * @param criterioCorrelacion
     */
    public CorrelatorTask(List<Slot> inputSlots, List<Slot> outputSlots, CorrelatorCriteria criterioCorrelacion)
    {
        super(inputSlots, outputSlots);
        this.criteria = criterioCorrelacion;
    }

    /**
     * <p>
     * This method will order the messages located on the input slots and will
     * correlate them with the xPath expression that we want and write them on
     * the output slots
     * </p>
     */
    @Override
    public void doTask()
    {
        List<Slot> slots = this.getInputSlots();
        for (Slot slot : slots)
        {
            Message msg;
            while ((msg = slot.read()) != null)        
            {
                criteria.applyCriteria(msg, this.getInputSlots(), this.getOutputSlots());
            }
        }
    }
}
