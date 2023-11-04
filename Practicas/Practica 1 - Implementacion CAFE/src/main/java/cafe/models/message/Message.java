package cafe.models.message;

import org.w3c.dom.Document;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class Message
{
    private Document document;
    private Document documentMetaData;
    
    private static final String NULL_DOCUMENT_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
            = "The document must not be null";
    private static final String NULL_DOCUMENT_METADATA_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
            = "The document metadata must not be null";

    public Message(Document document, Document documentMetaData)
    {
        if(document == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        if(documentMetaData == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_METADATA_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        this.document = document;
        this.documentMetaData = documentMetaData;
    }   

    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        if(document == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        this.document = document;
    }

    public Document getDocumentMetaData()
    {
        return documentMetaData;
    }

    public void setDocumentMetaData(Document documentMetaData)
    {
        if(documentMetaData == null)
        {
            throw new IllegalArgumentException(NULL_DOCUMENT_METADATA_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        this.documentMetaData = documentMetaData;
    }   
}