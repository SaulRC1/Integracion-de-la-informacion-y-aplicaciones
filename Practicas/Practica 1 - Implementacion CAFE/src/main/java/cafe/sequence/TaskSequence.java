package cafe.sequence;

/**
 *
 * @author Juan Alberto Dominguez Vazquez
 */
public class TaskSequence implements Sequence
{
    private static long TASK_SEQUENCE_NUMBER = 0;
    private static TaskSequence taskSequenceInstance = new TaskSequence();
    
    private TaskSequence()
    {
        
    }

    @Override
    public long getNextIdentifier()
    {
        long nextIdentifier = TASK_SEQUENCE_NUMBER;

        TASK_SEQUENCE_NUMBER++;

        return nextIdentifier;
    }
    
    public static TaskSequence getTaskSequenceInstance()
    {
        return taskSequenceInstance;
    }
}
