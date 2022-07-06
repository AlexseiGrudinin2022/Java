public class PhoneOutput {

    private final PhoneInput phoneInput = new PhoneInput();
    private final PhoneInput subInput = new PhoneInput();
    private String phone;
    private String name;
    private boolean exit;


    public boolean isExit() {
        return exit;
    }

    public boolean isInput() {
        return (name != null || phone != null);
    }

    private boolean isSearchByCommand(PhoneInput phone) {
        return !phone.getCommand().isEmpty();
    }

    private boolean isSearchByName(PhoneInput phone) {

        return !phone.getNamePerson().isEmpty();
    }

    private boolean isSearchByPhone(PhoneInput phone) {

        return !phone.getPhoneNumber().isEmpty();
    }

    public void runAction() {

        phoneInput.parseDump();

        if (isSearchByCommand(phoneInput)) {
            runCommand(phoneInput.getCommand());
        } else if (isSearchByName(phoneInput)) {
            name = phoneInput.getNamePerson();

            var phones = PhoneDump.phoneBook.getPhonesByName(name);

            if (phones.size() == 0) {
                System.out.println("Такого имени в телефонной книге нет!");
                inputPhone();
            } else {
                for (String s : phones) {
                    System.out.println(s);
                }
                name = null;
            }
        } else if (isSearchByPhone(phoneInput)) {
            phone = phoneInput.getPhoneNumber();

            var names = PhoneDump.phoneBook.getNameByPhone(phone);

            if (names.isEmpty()) {
                System.out.println("Такого номера нет в телефонной книге!");
            } else {
                System.out.println("Данный номер уже есть в телефонной книге, имя для данного номера будет перезаписано!");
            }
            inputName();
        }

        if (isInput()) {
            addKontact();
        }

    }


    private void inputDumps(String message, String expectedToBeFilledParam) {

        boolean isComplete;

        do {
            System.out.println(message);
            subInput.parseDump();
            isComplete = ((expectedToBeFilledParam.isEmpty() && isSearchByPhone(subInput)) ||
                    (!expectedToBeFilledParam.isEmpty() && isSearchByName(subInput)));

            if (!isComplete) {
                if (isSearchByCommand(subInput))
                    System.out.println("Ввод команды недопустим во время добавления контакта!");
            }

        } while (!isComplete);
    }

    private void runCommand(String command) {
        switch (command) {
            case "LIST": {

                for (String s :  PhoneDump.phoneBook.getAllContacts()) {
                    System.out.println(s);
                }

                break;
            }
            case "EXIT": {
                exit = true;
                break;
            }
        }
    }

    private void addKontact() {
        PhoneDump.phoneBook.addContact(phone, name);
        System.out.println("Контакт \"" + name + " - " + phone + "\" сохранен!");
    }

    private void inputPhone() {

        if (phone == null) {
            inputDumps("Введите номер телефона для абонента \"" + name + "\"", "");
            phone = subInput.getPhoneNumber();
        }
    }

    private void inputName() {

        if (name == null) {
            inputDumps("Введите имя для телефона \"" + phone + "\"", "name");
            name = subInput.getNamePerson();
        }
    }


}
