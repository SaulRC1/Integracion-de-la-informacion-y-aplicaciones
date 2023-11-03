package cafe.models.port;

import cafe.sequence.PortSequence;
import cafe.sequence.Sequence;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public abstract class Port
{
    private final long id;

    public Port()
    {
        Sequence portSequence = PortSequence.getPortSequenceInstance();
        this.id = portSequence.getNextIdentifier();
    }

    public long getId()
    {
        return id;
    }
}
