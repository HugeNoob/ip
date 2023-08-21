/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {

    // The date/time of the deadline.
    private String endTime;

    /**
     * Creates a new Deadline object.
     * 
     * @param description The description of the deadline.
     * @param endTime The date/time of the deadline.
     */
    public Deadline(String description, String endTime) {
        super(description);
        this.endTime = endTime;
    }

    /**
     * Returns the string representation of the deadline.
     * 
     * @return The string representation of the deadline. 
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), endTime);
    }
}
