import java.util.Scanner;

public class PhoneInput {
    private String phoneNumber = "";
    private String namePerson = "";
    private String command = "";

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public String getCommand()
    {
        return command;
    }

    public PhoneInput() {
        phoneNumber = "";
        namePerson = "";
        command = "";
    }


    public void parseDump() {

        String input = (new Scanner(System.in)).nextLine().replaceAll("\\s+", " ").trim();

        if (input.matches(PhoneBook.REG_PHONE)) {
            phoneNumber = input;
        } else if (input.toUpperCase().matches(PhoneBook.REG_COMAND)) {
            command = input.toUpperCase();
        } else if (input.matches(PhoneBook.REG_NAME)) {
            namePerson = input;
        } else {
            System.out.println("Введенные данные не соответствуют требованию ввода!");
        }

    }

}
