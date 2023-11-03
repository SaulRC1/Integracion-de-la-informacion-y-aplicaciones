package cafe.models.port;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class Port
{

    private UUID id;

    public Port()
    {
        this.id = UUID.randomUUID();
    }

    public UUID getId()
    {
        return id;
    }
}
