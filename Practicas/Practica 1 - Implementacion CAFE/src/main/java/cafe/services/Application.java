package cafe.services;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.port.EntryPort;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class Application
{
    /**
     * This method will load all the orders in the entry
     * @param entryPort the entry port where the messages will be written
     */
    public void loadOrders(EntryPort entryPort)
    {
        try
        {
            // Ruta del archivo XML
            String directoryPath = "src\\main\\resources\\comandas\\";
            MessageBuilder messageBuilder = new MessageBuilder();
             
            // Procesar 9 archivos XML secuencialmente
            for (int i = 1; i <= 9; i++)
            {
                String filePath = directoryPath + "order" + i + ".xml";

                // Parsear el documento XML
                Document xmlDocument = XMLDocumentParser.parseXMLDocument(filePath);

                // Construir un mensaje con el documento XML           
                Message message = messageBuilder.buildMessageWithEmptyMetadata(xmlDocument);

                // Escribir el mensaje en el EntryPort
                entryPort.write(message);
            }
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
           Logger.getLogger(Application.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}