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

    public Message(Document document, Document documentMetaData)
    {
        this.document = document;
        this.documentMetaData = documentMetaData;
    }   

    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        this.document = document;
    }

    public Document getDocumentMetaData()
    {
        return documentMetaData;
    }

    public void setDocumentMetaData(Document documentMetaData)
    {
        this.documentMetaData = documentMetaData;
    }
    
}