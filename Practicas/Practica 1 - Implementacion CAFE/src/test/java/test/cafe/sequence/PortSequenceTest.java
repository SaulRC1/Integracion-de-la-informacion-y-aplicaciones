package test.cafe.sequence;

import cafe.sequence.PortSequence;
import cafe.sequence.Sequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class PortSequenceTest 
{
    @Test
    public void test_id_uniqueness()
    {
        Sequence portSequence = PortSequence.getPortSequenceInstance();
        
        long expectedIdentifier = 9;
        long lastIdentifier = -1;
        
        for (int i = 0; i < 10; i++)
        {
            lastIdentifier = portSequence.getNextIdentifier();
        }
        
        Assertions.assertEquals(expectedIdentifier, lastIdentifier);
    }
}
