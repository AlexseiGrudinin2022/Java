import java.util.*;

public class PhoneBook {

    // лучше использовать 1 имя к нескольким телефонам, т.е ключ - имя, значение - телефон
    // в другом случае я бы сделал ключем именно число, а не строку

    TreeMap<String, TreeSet<String>> phonesBook = new TreeMap<>();

    final static String REG_PHONE = "\\d{11}+";
    final static String REG_NAME = "[A-Za-zА-Яа-яёЁ]+";
    final static String REG_COMAND = "LIST|EXIT";


    public boolean addContact(String phone, String name) {
        // проверьте корректность формата имени и телефона
        // если такой номер уже есть в списке, то перезаписать имя абонента\


        if (phone.matches(REG_PHONE) && name.matches(REG_NAME)) {


            String searchName = getNameByPhone(phone);
            Set<String> numbers = getPhonesByName(name);
            if (numbers.isEmpty() && searchName.isEmpty())
                phonesBook.computeIfAbsent(name, k -> new TreeSet<>()).add(phone);


            if (searchName.isEmpty())
                phonesBook.computeIfAbsent(name, k -> new TreeSet<>()).add(phone);
            else {
                String key = searchName.substring(0, searchName.indexOf(" "));
                numbers = getPhonesByName(key);
                phonesBook.get(key).remove(phone);

                if (phonesBook.get(key).size() == 0) phonesBook.remove(key);

                for (String s : numbers)
                    phonesBook.computeIfAbsent(name, k -> new TreeSet<>()).add(phone);
            }


            return true;

        } else {
            System.out.println("Имя или телефон не соответствуют регламенту ввода");
        }

        return false;
    }

    public String getNameByPhone(String phone) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найдены - вернуть пустую строку

        String output = "";

        if (phonesBook.size() != 0) {

            Set<String> keys = phonesBook.keySet();

            for (String key : keys) {
                String joins = String.join(", ", phonesBook.get(key));

                if (joins.contains(phone)) {
                    output = key + " - " + joins;
                    break;
                }
            }

        }

        return output;
    }


    public Set<String> getPhonesByName(String name) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найден - вернуть пустой TreeSet
        boolean isKeyTrue = phonesBook.containsKey(name);

        if (isKeyTrue) {
            Set<String> output = new TreeSet<>();
            output.add(name + " - " + String.join(", ", phonesBook.get(name)));
            return output;

        } else
            return new TreeSet<>();
    }

    public Set<String> getAllContacts() {
        // формат одного контакта "Имя - Телефон"
        // если контактов нет в телефонной книге - вернуть пустой TreeSet


        if (phonesBook.size() != 0) {
            Set<String> output = new TreeSet<>();
            Set<String> keys = phonesBook.keySet();
            for (String key : keys) {
                String getKeys = String.join(", ", phonesBook.get(key));
                output.add(key + " - " + getKeys);
            }
            return output;
        } else {
            System.out.println("Телефонная книга пуста!");
            return new TreeSet<>();
        }
    }
}