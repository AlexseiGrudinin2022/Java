public class Main {

    public static void main(String[] args) {

        boolean exit;

        do {

            PhoneOutput phoneOutput = new PhoneOutput();

            if (!phoneOutput.isInput()) {
                System.out.println("\nВведите имя, телефон или команду...");
            }

            phoneOutput.runAction();

            exit = phoneOutput.isExit();

        } while (!exit);
        System.out.println("До свидания!");

    }
}
