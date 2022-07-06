import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    final String REGEX_NAME_SURNAME = "[A-Za-zА-Яа-яёЁ]+";
    final String REG_PHONE = "\\+\\d{11}+";
    final String REG_EMAIL = "^([\\w\\d_-]+\\.)*[\\w\\d_-]+@[\\w\\d_-]+(\\.[\\w\\d_-]+)*\\.[\\w]{2,5}$";


    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws IncorrectEmailException, IncorrectPhoneNumberException {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");

        if (components.length != 4) {
            throw new IllegalArgumentException("Введите в верном формате команду add:\n Name Surname email@email.ru +79112223344");
        }
        if (!components[INDEX_NAME].matches(REGEX_NAME_SURNAME)) {
            throw new IllegalArgumentException("Имя должно состоять из букв русского или английского алфавита!");
        }
        if (!components[INDEX_SURNAME].matches(REGEX_NAME_SURNAME)) {
            throw new IllegalArgumentException("Фамилия должна состоять из букв русского или английского алфавита!");
        }
        if (!components[INDEX_EMAIL].matches(REG_EMAIL))  {
            throw new IncorrectEmailException("message",components[INDEX_EMAIL]);
        }


        if (components[INDEX_PHONE].charAt(0) == '8') {
            components[INDEX_PHONE] = "+7" + components[INDEX_PHONE].substring(1);
        }

        System.out.println(components[INDEX_PHONE]);
        if (!components[INDEX_PHONE].matches(REG_PHONE)) {
            throw new IncorrectPhoneNumberException("Телефон должен состоять из 12 символов! Формат: +79991112233",components[INDEX_PHONE]);
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));

    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}