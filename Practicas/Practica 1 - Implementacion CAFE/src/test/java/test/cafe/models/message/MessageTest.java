package test.cafe.models.message;

import cafe.models.message.Message;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class MessageTest
{
    private static String documentToString(Document document)
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException ex)
        {
            Logger.getLogger(MessageTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    @Test
    public void testCloneMessage()
    {
        try
        {
            // Crear un documento original para el mensaje
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document originalDocument = builder.newDocument();
            Element rootElement = originalDocument.createElement("cafe_order");
            originalDocument.appendChild(rootElement);

            Document documentMetaData = builder.newDocument();
            Element metaRootElement = documentMetaData.createElement("metadata");

            // Crear el elemento <splitterID>
            Element splitterIDElement = documentMetaData.createElement("splitterID");

            // Crear el contenido dentro de <splitterID>
            Text splitterIDText = documentMetaData.createTextNode("1");

            // Adjuntar el contenido al elemento <splitterID>
            splitterIDElement.appendChild(splitterIDText);

            // Adjuntar el elemento <splitterID> a <metadata>
            metaRootElement.appendChild(splitterIDElement);

            // Adjuntar <metadata> al documento de metadatos
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
            
            System.out.println("Original Document:\n" + documentToString(originalMessage.getDocumentMetaData()));
            System.out.println("Original Document:\n" + documentToString(originalMessage.getDocument()));
            System.out.println("Cloned Document:\n" + documentToString(clonedMessage.getDocumentMetaData()));           
            System.out.println("Cloned Document:\n" + documentToString(clonedMessage.getDocument()));
            
            assertEquals(false, diffDocument.hasDifferences());
            assertEquals(false, diffMetaData.hasDifferences());

        } catch (ParserConfigurationException | CloneNotSupportedException ex)
        {
            Logger.getLogger(MessageTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}