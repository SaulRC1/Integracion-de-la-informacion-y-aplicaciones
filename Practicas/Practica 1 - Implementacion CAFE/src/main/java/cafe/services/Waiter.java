package cafe.services;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.port.ExitPort;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The waiter for the CAFE application.
 * 
 * <p>
 * The waiter receives the final order and dumps it into a XML document.
 * </p>
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class Waiter
{
    private ExitPort exitPort;
    
    private String orderDestinationFile;
    
    /**
     * Builds a waiter.
     * 
     * @param exitPort The exit port.
     * @param orderDestinationFile The destination XML file for the order 
     * received by the waiter.
     */
    public Waiter(ExitPort exitPort, String orderDestinationFile)
    {
        this.exitPort = exitPort;
        this.orderDestinationFile = orderDestinationFile;
    }
    
    /**
     * Performs the waiter functionality
     */
    public void performFunctionality()
    {
        MessageBuilder messageBuilder = new MessageBuilder();
        
        int orderNumber = 1;
        
        Message msg;
        
        while((msg = exitPort.read()) != null)
        {
            Document waiterOrder = messageBuilder.buildEmptyMessage().getDocument();
            
            Node rootElement = waiterOrder.createElement("waiter_order");
            waiterOrder.appendChild(rootElement);
            
            Document messageMetadata = msg.getDocumentMetaData();
            
            Node importedMetadata = waiterOrder.importNode(messageMetadata.getDocumentElement(), true);
            rootElement.appendChild(importedMetadata);
            
            Document messageBody = msg.getDocument();
            
            Node importedBody = waiterOrder.importNode(messageBody.getDocumentElement(), true);
            rootElement.appendChild(importedBody);
            
            try
            {
                XMLDocumentParser.writeXMLDocument(waiterOrder, (orderDestinationFile + orderNumber));
                
                orderNumber++;
            } catch (IOException | TransformerException ex)
            {
                Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
