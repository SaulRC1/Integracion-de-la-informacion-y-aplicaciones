package cafe.models.task.modifying;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ContextEnricherTask extends Task
{

    private String placeToContext;

    /**
     * Will build a context enricher
     *
     * @param inputSlot Numbers of input slots that the task in going to have
     * @param outputSlot Numbers of output slots that the task in going to have
     * @param contextSlot The slot where the task in going to take the context
     * to add
     * @param placeToContext The place where we want to put the new information
     */
    public ContextEnricherTask(Slot inputSlot, Slot outputSlot, Slot contextSlot, String placeToContext)
    {
        super(List.of(inputSlot, contextSlot), List.of(outputSlot));
        this.placeToContext = placeToContext;
    }

    /**
     * <p>
     * This method will add the message located in the context slot and add it
     * to the messages in the input slot where the xPath expression say
     * </p>
     */
    @Override
    public void doTask()
    {
        Slot inputSlot = getInputSlots().get(0);
        Slot outputSlot = getOutputSlots().get(0);
        Slot contextSlot = getInputSlots().get(1);

        Message msg;

        while ((msg = inputSlot.read()) != null)
        {
            // Retrieve messages from slots
            Message contextMessage = contextSlot.read();

            // Get documents from messages
            Document inputDocument = msg.getDocument();
            Document contextDocument = contextMessage.getDocument();

            // Add the content of the context document to the input document
            mergeDocuments(inputDocument, contextDocument, this.placeToContext);

            // Write the updated input document to the output slot
            outputSlot.write(msg);
        }
    }

    private void mergeDocuments(Document target, Document source, String placeToContext)
    {
        try
        {
            // Find the target node where content should be added based on the placeToContext parameter
            NodeList targetNodes = target.getElementsByTagName(placeToContext);

            if (targetNodes.getLength() > 0)
            {
                Node targetNode = targetNodes.item(0);

                // Get the root node from the context document
                Node sourceRootNode = source.getDocumentElement();
                Node importedSourceRootNode = target.importNode(sourceRootNode, true);
                targetNode.appendChild(importedSourceRootNode);             
            } else
            {
                System.out.println("Target node not found: " + placeToContext);
            }
        } catch (DOMException ex)
        {
            Logger.getLogger(ContextEnricherTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
