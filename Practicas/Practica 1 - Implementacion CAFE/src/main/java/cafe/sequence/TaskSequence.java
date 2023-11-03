package cafe.sequence;

import cafe.models.task.Task;

/**
 * Sequence for all the {@link Task} present in the program.
 * 
 * @author Juan Alberto Dominguez Vazquez
 */
public class TaskSequence implements Sequence
{
    private static long TASK_SEQUENCE_NUMBER = 0;
    private static final TaskSequence taskSequenceInstance = new TaskSequence();
    
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
    
    /**
     * This method will return the {@link TaskSequence} instance.
     * 
     * @return The {@link TaskSequence} instance.
     */
    public static TaskSequence getTaskSequenceInstance()
    {
        return taskSequenceInstance;
    }
}
