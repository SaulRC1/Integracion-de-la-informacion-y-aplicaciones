package cafe;

import cafe.models.message.Message;
import cafe.models.port.EntryExitPort;
import cafe.models.port.EntryPort;
import cafe.models.slot.Slot;
import cafe.models.task.routing.CorrelatorTask;
import cafe.models.task.routing.DistributorTask;
import cafe.models.task.routing.ReplicatorTask;
import cafe.models.task.routing.distributor.CafeDistributorCriteria;
import cafe.models.task.routing.distributor.DistributorCriteria;
import cafe.models.task.transforming.SplitterTask;
import cafe.models.task.transforming.TranslatorTask;
import cafe.models.task.transforming.translator.DrinksBarmanTranslator;
import cafe.models.task.transforming.translator.Translator;
import cafe.services.Application;
import cafe.services.ColdDrinksBarman;
import cafe.xml.XMLDocumentParser;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;

public class Practica1_IIA_CAFE
{

    public static void main(String[] args)
    {
        Application app = new Application();
        Slot EntryPortSlot = new Slot();
        EntryPort entryPort = new EntryPort(EntryPortSlot);
        app.loadOrders(entryPort);

        /*List<Message> inputMessages = entryPort.getSlot().getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        
        String xPathExpression = "/cafe_order/drinks/drink";
        Slot splitterEntrySlot = new Slot();
        Slot splitterOutputSlot = new Slot();
        List<Message> entryMessages = entryPort.getSlot().getMessages();
        for (Message entryMessage : entryMessages)
        {
            splitterEntrySlot.write(entryMessage);
        }
        
        SplitterTask splitter = new SplitterTask(splitterEntrySlot, splitterOutputSlot, xPathExpression);
        splitter.doTask();

        /*List<Message> inputMessages = splitter.getOutputSlots().get(0).getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        List<Slot> distributorInputSlots = new ArrayList<>();
        List<Slot> distributorOutputSlots = new ArrayList<>();

        Slot distributorSlotColdDrinks = new Slot();
        Slot distributorSlotHotDrinks = new Slot();

        distributorInputSlots.add(splitter.getOutputSlots().get(0));
        distributorOutputSlots.add(distributorSlotColdDrinks);
        distributorOutputSlots.add(distributorSlotHotDrinks);

        DistributorCriteria cafeDistributorCriteria = new CafeDistributorCriteria();
        DistributorTask distributor = new DistributorTask(distributorInputSlots, distributorOutputSlots, cafeDistributorCriteria);

        distributor.doTask();
        /*List<Message> inputMessages = distributor.getOutputSlots().get(0).getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/

        List<Slot> replicatorOutputSlots = new ArrayList<>();
        Slot replicatorInputSlot = distributor.getOutputSlots().get(0);

        Slot replicatorOutputSlot_1 = new Slot();
        Slot replicatorOutputSlot_2 = new Slot();

        replicatorOutputSlots.add(replicatorOutputSlot_1);
        replicatorOutputSlots.add(replicatorOutputSlot_2);

        ReplicatorTask replicator = new ReplicatorTask(replicatorInputSlot, replicatorOutputSlots);
        replicator.doTask();
        /*List<Message> inputMessages = replicator.getOutputSlots().get(0).getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/

        Slot translatorInputSlot = replicator.getOutputSlots().get(0);
        Slot translatorOutputSlot = new Slot();
        Translator drinksBarmanTranslator = new DrinksBarmanTranslator();
        
        TranslatorTask translatorTask = new TranslatorTask(translatorInputSlot, translatorOutputSlot, drinksBarmanTranslator);
        translatorTask.doTask();
        /*List<Message> inputMessages = translatorTask.getOutputSlots().get(0).getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        
        Slot entryExitPortOutputSlot = new Slot();
        EntryExitPort entryExitPort = new EntryExitPort(entryExitPortOutputSlot, translatorTask.getOutputSlots().get(0));
        
        ColdDrinksBarman coldDrinksBarman = new ColdDrinksBarman(entryExitPort);
        
        List<Message> entryExitPortMessages = entryExitPortOutputSlot.getMessages();
        for (Message entryExitPortMessage : entryExitPortMessages)
        {
            entryExitPort.writeIntoInputSlot(entryExitPortMessage);
        }
        
        coldDrinksBarman.performFunctionality();
        
        /*List<Message> inputMessages = entryExitPort.getOutputSlot().getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        
        
        List<Slot> inputSlots = new ArrayList<>();
        List<Slot> outputSlots = new ArrayList<>();
        String CorrelatorXpathExpression = "/metadata/order_id/text()";
        
        Slot correlatorOutput_1 = new Slot();
        Slot correlatorOutput_2 = new Slot();
        Slot correlatorInputSlot_1 = replicator.getOutputSlots().get(1);
        Slot correlatorInputSlot_2 = entryExitPort.getOutputSlot();
        
        outputSlots.add(correlatorOutput_1);
        outputSlots.add(correlatorOutput_2);
        inputSlots.add(correlatorInputSlot_1);
        inputSlots.add(correlatorInputSlot_2);
        
        CorrelatorTask correlator = new CorrelatorTask(inputSlots, outputSlots, CorrelatorXpathExpression);
        correlator.doTask();
        
        /*List<Message> inputMessages = correlator.getOutputSlots().get(0).getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        
        Slot contextEnricherContextSlot = correlatorOutput_1;
        Slot contextEnricherInputSlot = correlatorOutput_2;
    }
}
