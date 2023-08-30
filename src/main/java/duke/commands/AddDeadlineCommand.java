package duke.commands;

import duke.tasks.TaskList;
import duke.tasks.TaskType;

/**
 * Represents a command to add a deadline task.
 */
public class AddDeadlineCommand extends Command {

    /**
     * Constructs a new AddDeadlineCommand object.
     *
     * @param taskList The current list of tasks.
     * @param args The arguments supplied by the user.
     */
    public AddDeadlineCommand(TaskList taskList, String args) {
        super(CommandType.ADD_DEADLINE, taskList, args);
    }

    /**
     * Adds a deadline task to the list of tasks.
     */
    @Override
    public void execute() {
        this.taskList.addTask(TaskType.DEADLINE, args);
    }
}
