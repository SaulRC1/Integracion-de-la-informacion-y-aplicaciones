package cafe.models.task.routing.distributor;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.transforming.AggregatorTask;
import cafe.xml.XPathParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cafe.models.task.transforming.SplitterTask;

/**
 * This distributor criteria from CAFE expects messages from a
 * {@link SplitterTask} where drinks have been splitted into single drink
 * messages with the following format:
 *
 * <p>
 * <code>
 * &lt;drink&gt;<br>
 * &nbsp;&lt;name&gt;guarana&lt;/name&gt;<br>
 * &nbsp;&lt;type&gt;cold&lt;/type&gt;<br>
 * &lt;/drink&gt;
 * </code>
 * </p>
 *
 * <p>
 * There are two types of drinks, hot and cold, and each must be distributed
 * towards the corresponding output {@link Slot}. In the case of this criteria
 * it is expected that the output slot for cold drinks is the 0 index slot in
 * the list and the output slot for hot drinks is the index 1 slot in the list.
 * </p>
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CafeDistributorCriteria implements DistributorCriteria
{

    private static final String DRINK_TYPE_COLD = "cold";
    private static final String DRINK_TYPE_HOT = "hot";

    @Override
    public void applyCriteria(Message msg, List<Slot> outputSlots)
    {
        Slot outputSlotForColdDrinks = outputSlots.get(0);
        Slot outputSlotForWarmDrinks = outputSlots.get(1);

        Document messageBody = msg.getDocument();

        NodeList drinkTypeNodeList = messageBody.getElementsByTagName("type");

        String drinkType = drinkTypeNodeList.item(0).getTextContent();

        if (drinkType.equals(DRINK_TYPE_COLD))
        {
            outputSlotForColdDrinks.write(msg);
        } else if (drinkType.equals(DRINK_TYPE_HOT))
        {
            outputSlotForWarmDrinks.write(msg);
        }
    }

}
