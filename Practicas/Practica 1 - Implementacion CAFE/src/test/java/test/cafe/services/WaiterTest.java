package test.cafe.services;

import cafe.models.port.ExitPort;
import cafe.models.slot.Slot;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class WaiterTest 
{
    @Test
    public void test()
    {
        String destinationFilePath = "C:\\Users\\SaulRC1\\Documents\\4º Ingeniería Informática\\IIA";
        
        Slot exitPortSlot = new Slot();
        ExitPort exitPort = new ExitPort(exitPortSlot);
    }
}
