package test.cafe.models.task.transforming;

import cafe.models.message.Message;
import cafe.models.message.MessageBuilder;
import cafe.models.slot.Slot;
import cafe.models.task.transforming.AggregatorTask;
import cafe.xml.WrongDocumentExtensionException;
import cafe.xml.XMLDocumentParser;
import cafe.xml.XPathParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class AggregatorTaskTest
{

    @Test
    public void test()
    {
        try
        {
            Document sample1Part1Metadata = XMLDocumentParser.parseXMLDocument("src/main/resources/AggregatorTaskTest/sample1_1_metadata.xml");
            
            XPathParser xpathParser = new XPathParser();
            
            Node sample1Part1SplitterId = (Node) xpathParser.executeXPathExpression(
                    "/metadata/splitterId", sample1Part1Metadata, 
                    XPathConstants.NODE);
            
            System.out.println("SplitterId: " + sample1Part1SplitterId.getChildNodes().item(0).getNodeValue());
            
            //Node imported = sample1Part1Metadata.importNode(sample1Part1SplitterId, true);
            //rootElement.appendChild(imported);
            
            XMLDocumentParser.printDocument(sample1Part1Metadata.getDocumentElement());
//Message msg = new Message(document, documentMetaData)
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void test_correct_aggregation()
    {
        try
        {
            Document sample1Part1Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_1_metadata.xml");
            Document sample1Part1Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_1_body.xml");
            
            Document sample1Part2Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_2_metadata.xml");
            Document sample1Part2Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_2_body.xml");
            
            Document sample1Part3Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_3_metadata.xml");
            Document sample1Part3Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_3_body.xml");
                    
            Message msg1 = new Message(sample1Part1Body, sample1Part1Metadata);
            Message msg2 = new Message(sample1Part2Body, sample1Part2Metadata);
            Message msg3 = new Message(sample1Part3Body, sample1Part3Metadata);
            
            
            Slot inputSlot = new Slot();
            Slot outputSlot = new Slot();
            
            List<Slot> inputSlots = new ArrayList<>();
            inputSlots.add(inputSlot);
            
            List<Slot> outputSlots = new ArrayList<>();
            outputSlots.add(outputSlot);
            
            AggregatorTask aggregatorTask = new AggregatorTask(inputSlots, outputSlots);
            
            inputSlot.write(msg1);
            inputSlot.write(msg2);
            inputSlot.write(msg3);
            
            aggregatorTask.doTask();
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void test_correct_aggregation_2_different_splitters()
    {
        try
        {
            Document sample1Part1Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_1_metadata.xml");
            Document sample1Part1Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_1_body.xml");
            
            Document sample1Part2Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_2_metadata.xml");
            Document sample1Part2Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_2_body.xml");
            
            Document sample1Part3Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_3_metadata.xml");
            Document sample1Part3Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample1_3_body.xml");
                    
            Message msg1 = new Message(sample1Part1Body, sample1Part1Metadata);
            Message msg2 = new Message(sample1Part2Body, sample1Part2Metadata);
            Message msg3 = new Message(sample1Part3Body, sample1Part3Metadata);
            
            Document sample2Part1Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample2_1_metadata.xml");
            Document sample2Part1Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample2_1_body.xml");
            
            Document sample2Part2Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample2_2_metadata.xml");
            Document sample2Part2Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample2_2_body.xml");
            
            Document sample2Part3Metadata = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample2_3_metadata.xml");
            Document sample2Part3Body = XMLDocumentParser.parseXMLDocument(
                    "src/main/resources/AggregatorTaskTest/sample2_3_body.xml");
            
            
            Message msg4 = new Message(sample2Part1Body, sample2Part1Metadata);
            Message msg5 = new Message(sample2Part2Body, sample2Part2Metadata);
            Message msg6 = new Message(sample2Part3Body, sample2Part3Metadata);
            
            Slot inputSlot = new Slot();
            Slot outputSlot = new Slot();
            
            List<Slot> inputSlots = new ArrayList<>();
            inputSlots.add(inputSlot);
            
            List<Slot> outputSlots = new ArrayList<>();
            outputSlots.add(outputSlot);
            
            AggregatorTask aggregatorTask = new AggregatorTask(inputSlots, outputSlots);
            
            inputSlot.write(msg1);
            inputSlot.write(msg2);
            inputSlot.write(msg3);
            inputSlot.write(msg4);
            inputSlot.write(msg5);
            inputSlot.write(msg6);
            
            aggregatorTask.doTask();
            
        } catch (ParserConfigurationException | SAXException | IOException | WrongDocumentExtensionException ex)
        {
            Logger.getLogger(AggregatorTaskTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
