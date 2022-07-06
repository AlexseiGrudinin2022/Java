public class IncorrectEmailException extends Exception {

    private final String email;

    public String getEmail() {
        return email;
    }

    public IncorrectEmailException(String message, String email) {
        super(message);
        this.email = email;
    }
}
