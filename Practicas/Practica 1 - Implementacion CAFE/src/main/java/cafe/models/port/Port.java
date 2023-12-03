package cafe.models.port;

import cafe.sequence.PortSequence;
import cafe.sequence.Sequence;

/**
 *
 * @author Saul Rodriguez Naranjo
 */
public abstract class Port
{
    private final long id;

    /**
     * Will build a port with a unique id
     */
    public Port()
    {
        Sequence portSequence = PortSequence.getPortSequenceInstance();
        this.id = portSequence.getNextIdentifier();
    }

    /**
     * This method will get id of the port
     * @return the id of the port
     */
    public long getId()
    {
        return id;
    }
}