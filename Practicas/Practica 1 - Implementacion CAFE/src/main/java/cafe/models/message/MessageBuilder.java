package cafe.models.message;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for building prefabricated messages ({@link Message}).
 *
 * @author Saúl Rodríguez Naranjo
 */
public class MessageBuilder
{

    private final DocumentBuilderFactory documentBuilderFactory;

    public MessageBuilder()
    {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
    }

    /**
     * Creates a {@link Message} with the body passed by parameter and an empty
     * metadata document prepared.
     *
     * @param messageBody The {@link Message} body
     * @return A {@link Message} with an empty metadata
     */
    public Message buildMessageWithEmptyMetadata(Document messageBody)
    {
        try
        {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

            Document documentMetadata = docBuilder.newDocument();

            Element rootElement = documentMetadata.createElement("metadata");
            documentMetadata.appendChild(rootElement);

            return new Message(messageBody, documentMetadata);

        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(MessageBuilder.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    /**
     * Builds a {@link Message} with the metadata passed as an argument and an
     * empty body.
     *
     * @param messageMetadata The {@link Message} metadata.
     * @return A {@link Message} with an empty body.
     */
    public Message buildMessageWithEmptyBody(Document messageMetadata)
    {
        try
        {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

            Document messageBody = docBuilder.newDocument();

            return new Message(messageBody, messageMetadata);

        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(MessageBuilder.class.getName()).log(Level.SEVERE,
                    ex.getMessage(), ex);
        }

        return null;
    }

    /**
     * Builds a {@link Message} with an empty body and an empty metadata.
     *
     * @return An empty {@link Message}.
     */
    public Message buildEmptyMessage()
    {
        try
        {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

            Document documentMetadata = docBuilder.newDocument();

            Element rootElement = documentMetadata.createElement("metadata");
            documentMetadata.appendChild(rootElement);

            Document messageBody = docBuilder.newDocument();

            return new Message(messageBody, documentMetadata);

        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(MessageBuilder.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }
}
