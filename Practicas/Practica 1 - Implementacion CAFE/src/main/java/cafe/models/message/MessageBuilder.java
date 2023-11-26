package cafe.models.message;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for building prefabricated messages
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
     * Creates a message with the body passed by parameter and an empty metadata
     * document prepared.
     * 
     * @param messageBody The message body
     * @return A message with an empty metadata
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

    public Message buildMessageWithEmptyBody()
    {
        return null;
    }
}
