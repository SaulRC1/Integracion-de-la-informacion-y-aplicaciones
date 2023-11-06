package cafe.xml;

/**
 * Exception used when the file extension is not the one the program is expecting.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class WrongDocumentExtensionException extends Exception
{

    public WrongDocumentExtensionException(String message)
    {
        super(message);
    }
    
}
