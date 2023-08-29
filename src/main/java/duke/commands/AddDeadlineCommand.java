package duke.commands;

import duke.tasks.TaskList;
import duke.tasks.TaskType;

public class AddDeadlineCommand extends Command {

    public AddDeadlineCommand(CommandType commandType, TaskList taskList, String args) {
        super(commandType, taskList, args);
    }

    @Override
    public void execute() {
        this.taskList.addTask(TaskType.DEADLINE, args);
    }
}