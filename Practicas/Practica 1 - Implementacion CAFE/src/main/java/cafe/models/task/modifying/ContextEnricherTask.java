package cafe.models.task.modifying;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.xml.XPathParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
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
    private String xPathExpression;

    /**
     *
     * @param inputSlot
     * @param outputSlot
     * @param contextSlot
     * @param xPathExpression
     */
    public ContextEnricherTask(Slot inputSlot, Slot outputSlot, Slot contextSlot, String xPathExpression)
    {
        super(List.of(inputSlot, contextSlot), List.of(outputSlot));
        this.xPathExpression = xPathExpression;
    }

    public String getxPathExpression()
    {
        return xPathExpression;
    }

    public void setxPathExpression(String xPathExpression)
    {
        this.xPathExpression = xPathExpression;
    }

    /**
     *
     */
    @Override
    public void doTask()
    {
        Slot inputSlot = getInputSlots().get(0);
        Slot outputSlot = getOutputSlots().get(0);
        Slot contextSlot = getInputSlots().get(1);

        // Retrieve messages from slots
        Message inputMessage = inputSlot.read();
        Message contextMessage = contextSlot.read();

        // Get documents from messages
        Document inputDocument = inputMessage.getDocument();
        Document contextDocument = contextMessage.getDocument();

        // Add the content of the context document to the input document
        mergeDocuments(inputDocument, contextDocument);

        // Write the updated input document to the output slot
        outputSlot.write(inputMessage);
    }

    private void mergeDocuments(Document target, Document source)
    {
        try
        {
            String xpathExpression = getxPathExpression();
            XPathParser xpath = new XPathParser();
            Object result = xpath.executeXPathExpression(xpathExpression, target, XPathConstants.NODE);
            Node targetMergeNode = (Node) result;
            
            Node newNode = target.createElement(xPathExpression.substring(xPathExpression.lastIndexOf('/') + 1));
            
            // Get the list of child nodes from the context document
            NodeList sourceChildNodes = source.getDocumentElement().getChildNodes();
        
            // Iterate over the child nodes of the context document
            for (int i = 0; i < sourceChildNodes.getLength(); i++)
            {
                Node sourceNode = sourceChildNodes.item(i);

                // Clone the node from the context document to avoid modifying it directly
                Node copiedNode = target.importNode(sourceNode, true);
                
                // Append the cloned node to the target node
                newNode.appendChild(copiedNode);
            }
            targetMergeNode.appendChild(newNode);
        } catch (XPathExpressionException | DOMException ex)
        {
            Logger.getLogger(ContextEnricherTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}