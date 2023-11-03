package cafe.models.connector;

import cafe.models.port.Port;
import cafe.services.Application;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class ConnectorImpl extends Connector
{
    public ConnectorImpl(Application app, Port port)
    {
        super(app, port);
    }
}
