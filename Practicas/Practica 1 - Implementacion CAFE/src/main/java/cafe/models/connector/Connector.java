package cafe.models.connector;

import cafe.models.port.Port;
import cafe.services.Application;

/**
 *
 * @author juald
 */
public abstract class Connector
{
    private Application app;
    private Port port;

    public Connector(Application app, Port port)
    {
        this.app = app;
        this.port = port;
    }

    public Application getCafe()
    {
        return app;
    }

    public void setCafe(Application app)
    {
        this.app = app;
    }

    public Port getPort()
    {
        return port;
    }

    public void setPort(Port port)
    {
        this.port = port;
    }   
}