package cafe.xml;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class XPathParser
{

    /**
     * Executes an XPath expression against a XML document.
     *
     * @param xpathExpression The XPath expression to execute.
     *
     * @param xmlDocument The XML document where the expression will be
     * executed.
     *
     * @param expectedData The expected result, please check
     * {@link XPathConstants} class.
     *
     * @return The result of the XPath expression execution.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     */
    public Object executeXPathExpression(String xpathExpression, Document xmlDocument,
            QName expectedData) throws XPathExpressionException
    {
        XPath xPath = XPathFactory.newInstance().newXPath();

        return xPath.compile(xpathExpression)
                .evaluate(xmlDocument, expectedData);
    }
    
}
