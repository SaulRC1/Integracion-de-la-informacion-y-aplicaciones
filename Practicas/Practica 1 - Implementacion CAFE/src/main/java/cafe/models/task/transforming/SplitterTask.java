package cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import cafe.xml.XPathParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class SplitterTask extends Task
{
    private String xPathExpression;
    private int splitterCounter = 0;
    public static final String SPLITTER_METADATA_TAG = "splitterID";

    /**
     * Will build a splitter
     * @param inputSlot Numbers of input slots that the task in going to have
     * @param outputSlot Numbers of output slots that the task in going to have
     * @param xPathExpression The xPath expression needed to know for what part split the message
     */
    public SplitterTask(Slot inputSlot, Slot outputSlot, String xPathExpression)
    {
        super(List.of(inputSlot), List.of(outputSlot));
        this.xPathExpression = xPathExpression;
        this.splitterCounter++;
    }

    /**
     * Will get the xPath expression
     * @return The xPath expression
     */
    public String getxPathExpression()
    {
        return xPathExpression;
    }

    /**
     * Will set the xPath expression that you desire
     * @param xPathExpression the xPath expression that we want
     */
    public void setxPathExpression(String xPathExpression)
    {
        this.xPathExpression = xPathExpression;
    }

    /**
     * <p>
     * This method will divide the messages from the input slot 
     * by where the xpath expression says and will write them to the output output.
     * </p>
     */
    @Override
    public void doTask()
    {
        Slot inputSlot = getInputSlots().get(0);
        Slot outputSlot = getOutputSlots().get(0);

        while (inputSlot.getSlotBufferSize() != 0)
        {
            Message inputMessage = inputSlot.read();

            Document document = inputMessage.getDocument();
            Document metadataDocument = inputMessage.getDocumentMetaData();
            String xpathExpression = getxPathExpression();

            try
            {
                // Crear un nuevo XPath
                XPathParser xpath = new XPathParser();

                // Evaluar la expresión XPath para obtener un NodeList de nodos que cumplen con la expresión
                Object result = xpath.executeXPathExpression(xpathExpression, document, XPathConstants.NODESET);
                NodeList nodeList = (NodeList) result;

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();              

                // Crear la etiqueta <splitterID> y agregarla a metadataRoot
                Element splitterIDElement = metadataDocument.createElement(SPLITTER_METADATA_TAG);
                splitterIDElement.appendChild(metadataDocument.createTextNode(String.valueOf(splitterCounter)));

                Element metadataRoot = metadataDocument.getDocumentElement();
                metadataRoot.appendChild(splitterIDElement);

                // Iterar sobre los nodos seleccionados
                for (int j = 0; j < nodeList.getLength(); j++)
                {
                    Document documentBody = documentBuilder.newDocument();
                    Node selectedNode = nodeList.item(j);
                    Node importedNode = documentBody.importNode(selectedNode, true);
                    documentBody.appendChild(importedNode);

                    Message outputMessage = new Message(documentBody, metadataDocument);
                    outputSlot.write(outputMessage);
                }
            } catch (XPathExpressionException | DOMException | ParserConfigurationException ex)
            {
                Logger.getLogger(SplitterTask.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}