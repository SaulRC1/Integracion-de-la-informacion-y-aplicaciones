package test.cafe.models.task.routing;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.routing.DistributorTask;
import cafe.models.task.routing.distributor.CafeDistributorCriteria;
import cafe.models.task.routing.distributor.DistributorCriteria;
import cafe.models.task.transforming.SplitterTask;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class DistributorTaskTest 
{
    @Test
    public void test_correct_distribution()
    {
        try
        {
            MessageBuilder messageBuilder = new MessageBuilder();
            
            Slot inputSlot = new Slot();
            
            List<Slot> inputSlots = new ArrayList<>();
            
            inputSlots.add(inputSlot);
            
            Slot coldDrinksOutputSlot = new Slot();
            Slot warmDrinksOutputSlot = new Slot();
            
            List<Slot> outputSlots = new ArrayList<>();
            
            outputSlots.add(coldDrinksOutputSlot);
            outputSlots.add(warmDrinksOutputSlot);
            
            DistributorCriteria cafeDistributorCriteria =  new CafeDistributorCriteria();
            
            DistributorTask cafeDistributorTask = new DistributorTask(inputSlots, outputSlots,
                    cafeDistributorCriteria);
            
            Document sampleColdDrink = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DistributorTaskTest/sample_cold_drink.xml");
            
            Message coldDrinkMessage = messageBuilder.buildMessageWithEmptyMetadata(sampleColdDrink);
            
            Document sampleWarmDrink = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/DistributorTaskTest/sample_warm_drink.xml");
            
            Message warmDrinkMessage = messageBuilder.buildMessageWithEmptyMetadata(sampleWarmDrink);
            
            inputSlot.write(warmDrinkMessage);
            inputSlot.write(coldDrinkMessage);
            
            cafeDistributorTask.doTask();
            
            Assertions.assertEquals(1, coldDrinksOutputSlot.getSlotBufferSize());
            Assertions.assertEquals(1, warmDrinksOutputSlot.getSlotBufferSize());
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(DistributorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void test_correct_distribution_from_splitter_task()
    {
        try
        {
            MessageBuilder messageBuilder = new MessageBuilder();
            
            Document sampleMessageBodySplitter = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/SplitterTaskTest/sample1_1.xml");
            
            Message splitterMessage = messageBuilder.buildMessageWithEmptyMetadata(sampleMessageBodySplitter);
            
            Slot splitterInputSlot = new Slot();
            Slot splitterOutputSlot = new Slot();
            
            SplitterTask splitter = new SplitterTask(splitterInputSlot, splitterOutputSlot, 
                    "/cafe_order/drinks/drink");
            
            splitterInputSlot.write(splitterMessage);
            
            splitter.doTask();
            
            List<Slot> distributorInputSlots = new ArrayList<>();
            
            distributorInputSlots.add(splitterOutputSlot);
            
            Slot coldDrinksOutputSlot = new Slot();
            Slot warmDrinksOutputSlot = new Slot();
            
            List<Slot> distributorOutputSlots = new ArrayList<>();
            
            distributorOutputSlots.add(coldDrinksOutputSlot);
            distributorOutputSlots.add(warmDrinksOutputSlot);
            
            DistributorCriteria cafeDistributorCriteria =  new CafeDistributorCriteria();
            
            DistributorTask cafeDistributorTask = new DistributorTask(distributorInputSlots, 
                    distributorOutputSlots, cafeDistributorCriteria);
            
            cafeDistributorTask.doTask();
            
            Assertions.assertEquals(1, coldDrinksOutputSlot.getSlotBufferSize());
            Assertions.assertEquals(1, warmDrinksOutputSlot.getSlotBufferSize());
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(DistributorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
