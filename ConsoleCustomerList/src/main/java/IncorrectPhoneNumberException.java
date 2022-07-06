public class IncorrectPhoneNumberException extends Exception {

    private final String number;

    public String getNumber() {
        return number;
    }

    public IncorrectPhoneNumberException(String message, String number) {
        super(message);
        this.number = number;
    }
}
