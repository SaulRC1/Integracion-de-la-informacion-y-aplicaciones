package cafe;

import cafe.models.port.EntryPort;
import cafe.models.slot.Slot;
import cafe.services.Application;

public class Practica1_IIA_CAFE
{
    public static void main(String[] args)
    {
        Application app = new Application();
        Slot slot = new Slot();
        EntryPort entryPort = new EntryPort(slot);   
        app.loadOrders(entryPort);
    }
}