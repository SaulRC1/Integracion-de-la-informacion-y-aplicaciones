package cafe;

import cafe.models.message.Message;
import cafe.models.port.EntryExitPort;
import cafe.models.port.EntryPort;
import cafe.models.port.ExitPort;
import cafe.models.slot.Slot;
import cafe.models.task.modifying.ContextEnricherTask;
import cafe.models.task.routing.CorrelatorTask;
import cafe.models.task.routing.DistributorTask;
import cafe.models.task.routing.MergerTask;
import cafe.models.task.routing.ReplicatorTask;
import cafe.models.task.routing.correlator.CafeCorrelatorCriteria;
import cafe.models.task.routing.correlator.CorrelatorCriteria;
import cafe.models.task.routing.distributor.CafeDistributorCriteria;
import cafe.models.task.routing.distributor.DistributorCriteria;
import cafe.models.task.transforming.AggregatorTask;
import cafe.models.task.transforming.SplitterTask;
import cafe.models.task.transforming.TranslatorTask;
import cafe.models.task.transforming.translator.DrinksBarmanTranslator;
import cafe.models.task.transforming.translator.Translator;
import cafe.services.Application;
import cafe.services.ColdDrinksBarman;
import cafe.services.HotDrinksBarman;
import cafe.services.Waiter;
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

        // ------------------Rama bebidas frias---------------------------
        List<Slot> replicatorOutputSlots = new ArrayList<>();
        Slot replicatorInputSlot = distributor.getOutputSlots().get(0);

        Slot replicatorOutputSlot_1 = new Slot();
        Slot replicatorOutputSlot_2 = new Slot();

        replicatorOutputSlots.add(replicatorOutputSlot_1);
        replicatorOutputSlots.add(replicatorOutputSlot_2);

        ReplicatorTask replicator = new ReplicatorTask(replicatorInputSlot, replicatorOutputSlots);
        replicator.doTask();

        Slot translatorInputSlot = replicatorOutputSlot_1;
        Slot translatorOutputSlot = new Slot();
        Translator drinksBarmanTranslator = new DrinksBarmanTranslator();

        TranslatorTask translatorTask = new TranslatorTask(translatorInputSlot, translatorOutputSlot, drinksBarmanTranslator);
        translatorTask.doTask();

        Slot entryExitPortOutputSlot = new Slot();
        EntryExitPort entryExitPort = new EntryExitPort(entryExitPortOutputSlot, translatorTask.getOutputSlots().get(0));

        ColdDrinksBarman coldDrinksBarman = new ColdDrinksBarman(entryExitPort);

        List<Message> entryExitPortMessages = entryExitPortOutputSlot.getMessages();
        for (Message entryExitPortMessage : entryExitPortMessages)
        {
            entryExitPort.writeIntoInputSlot(entryExitPortMessage);
        }

        coldDrinksBarman.performFunctionality();

        List<Slot> inputSlots = new ArrayList<>();
        List<Slot> outputSlots = new ArrayList<>();
        CorrelatorCriteria criteria = new CafeCorrelatorCriteria();

        Slot correlatorOutput_1 = new Slot();
        Slot correlatorOutput_2 = new Slot();
        Slot correlatorInputSlot_1 = replicatorOutputSlot_2;
        Slot correlatorInputSlot_2 = entryExitPort.getOutputSlot();

        outputSlots.add(correlatorOutput_1);
        outputSlots.add(correlatorOutput_2);
        inputSlots.add(correlatorInputSlot_1);
        inputSlots.add(correlatorInputSlot_2);

        CorrelatorTask correlator = new CorrelatorTask(inputSlots, outputSlots, criteria);
        correlator.doTask();

        Slot contextEnricherContextSlot = correlatorOutput_2;
        Slot contextEnricherInputSlot = correlatorOutput_1;
        Slot contextEnricherOutputSlot = new Slot();

        ContextEnricherTask contextEnricher = new ContextEnricherTask(contextEnricherInputSlot, contextEnricherOutputSlot, contextEnricherContextSlot, "drink");
        contextEnricher.doTask();

        //---------------------- Rama bebidas Calientes ----------------------//
        List<Slot> replicator2OutputSlots = new ArrayList<>();
        Slot replicator2InputSlot = distributor.getOutputSlots().get(1);

        Slot replicator2OutputSlot_1 = new Slot();
        Slot replicator2OutputSlot_2 = new Slot();

        replicator2OutputSlots.add(replicator2OutputSlot_1);
        replicator2OutputSlots.add(replicator2OutputSlot_2);

        ReplicatorTask replicator2 = new ReplicatorTask(replicator2InputSlot, replicator2OutputSlots);
        replicator2.doTask();
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

        Slot translator2InputSlot = replicator2OutputSlot_1;
        Slot translator2OutputSlot = new Slot();
        Translator drinksBarmanTranslator2 = new DrinksBarmanTranslator();

        TranslatorTask translator2Task = new TranslatorTask(translator2InputSlot, translator2OutputSlot, drinksBarmanTranslator2);
        translator2Task.doTask();
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

        Slot entryExitPort2OutputSlot = new Slot();
        EntryExitPort entryExit2Port = new EntryExitPort(entryExitPort2OutputSlot, translator2Task.getOutputSlots().get(0));

        HotDrinksBarman HotDrinksBarman = new HotDrinksBarman(entryExit2Port);

        List<Message> entryExit2PortMessages = entryExitPort2OutputSlot.getMessages();
        for (Message entryExitPortMessage : entryExit2PortMessages)
        {
            entryExit2Port.writeIntoInputSlot(entryExitPortMessage);
        }

        HotDrinksBarman.performFunctionality();

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
        List<Slot> inputSlots2 = new ArrayList<>();
        List<Slot> outputSlots2 = new ArrayList<>();
        CorrelatorCriteria criteria2 = new CafeCorrelatorCriteria();

        Slot correlatorOutput_1_2 = new Slot();
        Slot correlatorOutput_2_2 = new Slot();
        Slot correlatorInputSlot_1_2 = replicator2OutputSlot_2;
        Slot correlatorInputSlot_2_2 = entryExit2Port.getOutputSlot();

        outputSlots2.add(correlatorOutput_1_2);
        outputSlots2.add(correlatorOutput_2_2);
        inputSlots2.add(correlatorInputSlot_1_2);
        inputSlots2.add(correlatorInputSlot_2_2);

        CorrelatorTask correlator2 = new CorrelatorTask(inputSlots2, outputSlots2, criteria2);
        correlator2.doTask();

        /*List<Message> inputMessages = correlatorOutput_1.getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }
        
        List<Message> messagesFromCorrelator = correlatorOutput_2.getMessages();
        int j = 0;
        for (Message outputMessage : messagesFromCorrelator)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + j++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        Slot contextEnricher2ContextSlot = correlatorOutput_2_2;
        Slot contextEnricher2InputSlot = correlatorOutput_1_2;
        Slot contextEnricher2OutputSlot = new Slot();

        ContextEnricherTask contextEnricher2 = new ContextEnricherTask(contextEnricher2InputSlot, contextEnricher2OutputSlot, contextEnricher2ContextSlot, "drink");
        contextEnricher2.doTask();

        /*List<Message> inputMessages = contextEnricherOutputSlot.getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        //-------------------------FIN RAMA CALIENTE
        List<Slot> mergerInputSlots = new ArrayList<>();
        Slot mergerInputSlot1 = contextEnricherOutputSlot;
        Slot mergerInputSlot2 = contextEnricher2OutputSlot;

        mergerInputSlots.add(mergerInputSlot1);
        mergerInputSlots.add(mergerInputSlot2);

        Slot mergerOutputSlot = new Slot();

        MergerTask merger = new MergerTask(mergerInputSlots, mergerOutputSlot);
        merger.doTask();

        /*List<Message> inputMessages = mergerOutputSlot.getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        Slot aggregatortInputSlot = mergerOutputSlot;
        Slot aggregatorOutputSlot = new Slot();

        List<Slot> aggregatorInputSlots = new ArrayList<>();
        aggregatorInputSlots.add(aggregatortInputSlot);

        List<Slot> aggregatorOutputSlots = new ArrayList<>();
        aggregatorOutputSlots.add(aggregatorOutputSlot);

        AggregatorTask aggregatorTask = new AggregatorTask(aggregatorInputSlots, aggregatorOutputSlots);
        aggregatorTask.doTask();

        /*List<Message> inputMessages = aggregatorOutputSlot.getMessages();
        int i = 0;
        for (Message outputMessage : inputMessages)
        {
            Document messageBody = outputMessage.getDocument();
            Document messageMetadata = outputMessage.getDocumentMetaData();
            System.out.println("Mensaje " + i++ + ":");
            XMLDocumentParser.printDocument(messageMetadata.getDocumentElement());
            XMLDocumentParser.printDocument(messageBody.getDocumentElement());
        }*/
        String destinationFilePath = "C:\\Users\\juald\\OneDrive\\Escritorio\\Integracion-de-la-informacion-y-aplicaciones\\waiter_order";

        Slot exitPortSlot = aggregatorOutputSlot;
        ExitPort exitPort = new ExitPort(exitPortSlot);
        Waiter waiter = new Waiter(exitPort, destinationFilePath);
        waiter.performFunctionality();
    }
}
