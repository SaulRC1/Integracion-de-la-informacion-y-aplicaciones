package cafe.models.message;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class Message implements Cloneable
{

    private Document document;
    private Document documentMetaData;

    private static final String NULL_DOCUMENT_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
            = "The document must not be null";
    private static final String NULL_DOCUMENT_METADATA_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
            = "The document metadata must not be null";

    /**
     *
     * @param document
     * @param documentMetaData
     */
    public Message(Document document, Document documentMetaData)
    {
        if (document == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        if (documentMetaData == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_METADATA_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        this.document = document;
        this.documentMetaData = documentMetaData;
    }

    /**
     *
     * @return
     */
    public Document getDocument()
    {
        return document;
    }

    /**
     *
     * @param document
     */
    public void setDocument(Document document)
    {
        if (document == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        this.document = document;
    }

    /**
     *
     * @return
     */
    public Document getDocumentMetaData()
    {
        return documentMetaData;
    }

    /**
     *
     * @param documentMetaData
     */
    public void setDocumentMetaData(Document documentMetaData)
    {
        if (documentMetaData == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_METADATA_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        this.documentMetaData = documentMetaData;
    }

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        //Document clonedDocument = cloneDocument(this.document);
        //Document clonedDocumentMetaData = cloneDocument(this.documentMetaData);
        //return new Message(clonedDocument, clonedDocumentMetaData);
        Message clonedMessage = (Message) super.clone();
        clonedMessage.document = (Document) this.document.cloneNode(true);
        clonedMessage.documentMetaData = (Document) this.documentMetaData.cloneNode(true);
        return clonedMessage;
    }

    private Document cloneDocument(Document originalDocument)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document clonedDocument = builder.newDocument();
            Node originalRoot = originalDocument.getDocumentElement();
            Node clonedRoot = clonedDocument.importNode(originalRoot, true);
            clonedDocument.appendChild(clonedRoot);

            cloneNodes(originalRoot, clonedRoot);

            return clonedDocument;
        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    private void cloneNodes(Node originalNode, Node clonedNode)
    {
        NodeList children = originalNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node originalChild = children.item(i);
            Node clonedChild = clonedNode.getOwnerDocument().importNode(originalChild, true);
            clonedNode.appendChild(clonedChild);
            cloneNodes(originalChild, clonedChild); // Clonar también los hijos recursivamente
        }

        NamedNodeMap attributes = originalNode.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            Node originalAttribute = attributes.item(i);
            Node clonedAttribute = clonedNode.getOwnerDocument().importNode(originalAttribute, true);
            clonedNode.getAttributes().setNamedItem(clonedAttribute);
        }
    }
}
