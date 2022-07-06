public class IllegalCommandException extends Exception {
    private final String command;

    public String getCommand() {
        return command;
    }

    public IllegalCommandException(String message, String command) {
        super(message);
        this.command = command;
    }
}
