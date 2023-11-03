package cafe.sequence;

/**
 * This interface represents a numeric sequence that increments automatically.
 * 
 * @author Juan Alberto Dominguez Vazquez
 */
public interface Sequence 
{
    /**
     * This method will return the next immediate available identifier.
     * 
     * <p>
     * It must be taken into account that every time this method is called, the 
     * sequence number will increment by one.
     * </p>
     * 
     * @return The next available identifier.
     */
    public long getNextIdentifier();
}
