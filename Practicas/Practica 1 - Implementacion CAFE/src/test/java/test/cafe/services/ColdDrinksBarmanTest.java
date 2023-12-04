package test.cafe.services;

import cafe.services.ColdDrinksBarman;
import cafe.xml.XMLDocumentParser;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class ColdDrinksBarmanTest 
{

    @Test
    public void test()
    {
        ColdDrinksBarman coldDrinksBarman = new ColdDrinksBarman();
        
        Document cocaColaDrinkInfo = coldDrinksBarman.formatDrinkInformation("coca-cola");
        
        XMLDocumentParser.printDocument(cocaColaDrinkInfo.getDocumentElement());
    }
    
}
