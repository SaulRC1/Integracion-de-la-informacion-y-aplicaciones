package test.cafe.models.message;

import cafe.models.message.Message;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class MessageTest
{
    @Test
    public void testCloneMessage()
    {
        try
        {
            // Crear un documento original para el mensaje
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document originalDocument = builder.newDocument();
            Element rootElement = originalDocument.createElement("root");
            originalDocument.appendChild(rootElement);

            // Crear un documento original para los metadatos del mensaje
            Document documentMetaData = builder.newDocument();
            Element metaRootElement = documentMetaData.createElement("metadata");
            documentMetaData.appendChild(metaRootElement);

            // Crear un mensaje original
            Message originalMessage = new Message(originalDocument, documentMetaData);

            // Clonar el mensaje
            Message clonedMessage = (Message) originalMessage.clone();

            // Comparar los documentos utilizando XMLUnit
            Diff diffDocument = DiffBuilder.compare(originalMessage.getDocument())
                    .withTest(clonedMessage.getDocument())
                    .checkForSimilar()
                    .build();

            Diff diffMetaData = DiffBuilder.compare(originalMessage.getDocumentMetaData())
                    .withTest(clonedMessage.getDocumentMetaData())
                    .checkForSimilar()
                    .build();

            assertEquals(false, diffDocument.hasDifferences());
            assertEquals(false, diffMetaData.hasDifferences());

        } catch (ParserConfigurationException | CloneNotSupportedException ex)
        {
            Logger.getLogger(MessageTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}