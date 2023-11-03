package cafe.sequence;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class PortSequence implements Sequence
{

    private static long PORT_SEQUENCE_NUMBER = 0;
    private static PortSequence portSequenceInstance = new PortSequence();

    private PortSequence()
    {

    }

    @Override
    public long getNextIdentifier()
    {
        long nextIdentifier = PORT_SEQUENCE_NUMBER;

        PORT_SEQUENCE_NUMBER++;

        return nextIdentifier;
    }

    public static PortSequence getPortSequenceInstance() 
    {
        return portSequenceInstance;
    }
}
