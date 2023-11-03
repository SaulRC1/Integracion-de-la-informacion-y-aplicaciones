package cafe.sequence;

import cafe.models.port.Port;

/**
 * Sequence for all the {@link Port} present in the program.
 * 
 * @author Juan Alberto Dominguez Vazquez
 */
public class PortSequence implements Sequence
{

    private static long PORT_SEQUENCE_NUMBER = 0;
    private static final PortSequence portSequenceInstance = new PortSequence();

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

    /**
     * This method will return the {@link PortSequence} instance.
     * 
     * @return The {@link PortSequence} instance.
     */
    public static PortSequence getPortSequenceInstance() 
    {
        return portSequenceInstance;
    }
}
