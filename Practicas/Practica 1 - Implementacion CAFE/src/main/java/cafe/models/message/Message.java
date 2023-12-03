package cafe.models.message;

import org.w3c.dom.Document;

/**
 * The messages to be sent throughout the CAFE application.
 * 
 * <p>
 * These messages consist of a metadata where information outside the main 
 * content will be placed, and a body, where the main content resides.
 * </p>
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
     * Will build a message
     * @param document the document body of the message
     * @param documentMetaData the document metadata of the message
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
     * Will get the document body
     * @return the document body
     */
    public Document getDocument()
    {
        return document;
    }

    /**
     * Will set the document body with the document on the parameter
     * @param document The document body that we want
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
     * Will get the document metadata
     * @return the document metada
     */
    public Document getDocumentMetaData()
    {
        return documentMetaData;
    }

    /**
     * Will set the document metadata with the document on the parameter
     * @param documentMetaData The document metadata that we want
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
     * Will clone the message
     * @return the cloned message
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Message clonedMessage = (Message) super.clone();
        clonedMessage.document = (Document) this.document.cloneNode(true);
        clonedMessage.documentMetaData = (Document) this.documentMetaData.cloneNode(true);
        return clonedMessage;
    }
}