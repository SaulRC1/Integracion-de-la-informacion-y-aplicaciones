package cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.task.Task;
import cafe.models.slot.Slot;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
    private static int splitterCounter = 1;
    public static final String SPLITTER_METADATA_OPEN_TAG = "<splitterID>";
    public static final String SPLITTER_METADATA_CLOSE_TAG = "<\\splitterID>";

    public SplitterTask(Slot inputSlot, Slot outputSlot, String xPathExpression)
    {
        super(List.of(inputSlot), List.of(outputSlot));
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

    @Override
    public void doTask()
    {
        Slot inputSlot = getInputSlots().get(0);
        Slot outputSlot = getOutputSlots().get(0);

        List<Message> inputMessages = inputSlot.getMessages();

        if (inputMessages != null)
        {
            for (int i = 0; i < inputMessages.size(); i++)
            {
                Message inputMessage = inputMessages.get(i);

                // Obtener el documento y la expresión XPath
                Document document = inputMessage.getDocument();
                String xpathExpression = getxPathExpression();

                try
                {
                    // Crear un nuevo XPath
                    XPath xpath = XPathFactory.newInstance().newXPath();

                    // Compilar la expresión XPath
                    XPathExpression expr = xpath.compile(xpathExpression);

                    // Evaluar la expresión XPath para obtener un NodeList de nodos que cumplen con la expresión
                    NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

                    // Iterar sobre los nodos seleccionados y agregar la etiqueta al DocumentMetaData
                    for (int j = 0; j < nodeList.getLength(); j++)
                    {
                        Node selectedNode = nodeList.item(j);

                        // Obtener el DocumentMetaData actual
                        Document metaData = inputMessage.getDocumentMetaData();

                        // Crear la etiqueta <splitterID>1</splitterID>
                        Element splitterIDElement = metaData.createElement(SPLITTER_METADATA_OPEN_TAG);
                        splitterIDElement.appendChild(metaData.createTextNode("1"));

                        // Agregar la etiqueta al DocumentMetaData
                        metaData.getDocumentElement().appendChild(splitterIDElement);
                    }

                    // Escribir el mensaje actualizado en el outputSlot
                    outputSlot.write(inputMessage);

                } catch (XPathExpressionException | DOMException ex)
                {
                    Logger.getLogger(SplitterTask.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }

    private Document createNewDocument(org.w3c.dom.Node node)
    {
        try
        {
            // Crear un nuevo documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document newDocument = builder.newDocument();

            // Clonar los nodos del documento original y agregarlos al nuevo documento
            Node importedNode = newDocument.importNode(node, true);
            newDocument.appendChild(importedNode);

            return newDocument;
        } catch (ParserConfigurationException | DOMException ex)
        {
            Logger.getLogger(SplitterTask.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }
}
