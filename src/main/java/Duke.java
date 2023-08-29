import java.nio.file.Path;
import java.util.Scanner;

/**
 * Duke is a chatbot that helps you keep track of your tasks.
 * In this version, Duke is renamed to LilBro.
 */
public class Duke {

    // The task list is used to store the user's tasks.
    private static final TaskList list = new TaskList();

    /**
     * The command enum is used to store the valid commands that LilBro can
     * understand.
     */
    private static enum Command {
        EXIT("bye"),
        LIST_TASKS("list"),
        ADD_TODO("todo"),
        ADD_DEADLINE("deadline"),
        ADD_EVENT("event"),
        MARK_TASK("mark"),
        UNMARK_TASK("unmark"),
        DELETE_TASK("delete");

        // The string representation of the command.
        private final String value;

        /**
         * The constructor is used to create a new command with the given value.
         * 
         * @param value The string representation of the command.
         */
        private Command(String value) {
            this.value = value;
        }

        /**
         * The fromString method is used to get the command from the given string.
         * 
         * @param value The string representation of the command.
         * @return The command if it exists, null otherwise.
         */
        public static Command fromString(String value) {
            for (Command command : Command.values()) {
                if (command.value.equalsIgnoreCase(value)) {
                    return command;
                }
            }
            return null;
        }
    }

    /**
     * The main method is used to run the program.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Ui.greet();
        Ui.println("Checking for a save file...");

        String projectRoot = System.getProperty("user.dir");
        String path = Path.of(projectRoot, "data/duke.txt").toString();
        Storage storage = new Storage(path);
        try {
            storage.load(Duke.list);
        } catch (DukeException e) {
            Ui.println(e.getMessage());
        }
        Ui.println("OK, ready to roll");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            Command command = Command.fromString(parts[0]);
            String commandArgs = parts.length > 1 ? parts[1].trim() : "";
            try {
                if (!Duke.executeCommand(command, commandArgs)) {
                    break;
                }
            } catch (DukeException e) {
                Ui.println(e.getMessage());
            }
        }

        scanner.close();
        Ui.println("Before you go, let me save your tasks...");
        storage.save(Duke.list);
        Ui.bye();
    }

    /**
     * Executes the given command with the given arguments.
     * 
     * @param command The command to execute.
     * @param args    The arguments to pass to the command.
     * @throws DukeInvalidCommandException  If the command is invalid.
     * @throws DukeInvalidArgumentException If the arguments are invalid.
     * @return True if the command was executed successfully, false otherwise.
     */
    private static boolean executeCommand(Command command, String args)
            throws DukeInvalidCommandException, DukeInvalidArgumentException {
        if (command == null) {
            throw new DukeInvalidCommandException(
                    "Not sure what you're blabbering on about, please use a valid command.");
        }

        switch (command) {
        case EXIT:
            return false;

        case LIST_TASKS:
            Duke.list.listTasks();
            break;

        case MARK_TASK:
            try {
                Duke.list.markTaskDone(Integer.parseInt(args));
            } catch (NumberFormatException e) {
                throw new DukeInvalidArgumentException("Stop trolling me bro. Please enter a numeric index.");
            }
            break;

        case UNMARK_TASK:
            try {
                Duke.list.unmarkTaskDone(Integer.parseInt(args));
            } catch (NumberFormatException e) {
                throw new DukeInvalidArgumentException("Stop trolling me bro. Please enter a numeric index.");
            }
            break;

        case ADD_TODO:
            Duke.list.addTask(TaskType.TODO, args);
            break;

        case ADD_DEADLINE:
            Duke.list.addTask(TaskType.DEADLINE, args);
            break;

        case ADD_EVENT:
            Duke.list.addTask(TaskType.EVENT, args);
            break;

        case DELETE_TASK:
            try {
                Duke.list.deleteTask(Integer.parseInt(args));
            } catch (NumberFormatException e) {
                throw new DukeInvalidArgumentException("Stop trolling me bro. Please enter a numeric index.");
            }
            break;

        default:
            throw new DukeInvalidCommandException("I'm gonna be honest, no idea what you're saying.");
        }

        return true;
    }

}
