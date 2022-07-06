import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Movements {

    private final List<Record> recordsList = new ArrayList<>();
    private final static Logger MOVEMENTS_LOG = LogManager.getLogger(Movements.class);


    private static Map<String, Class> csvDefinition = Map.of(
            "Тип счёта", String.class,
            "Номер счета", String.class,
            "Валюта", String.class,
            "Дата операции", LocalDate.class,
            "Референс проводки", String.class,
            "Описание операции", String.class,
            "Приход", Double.class,
            "Расход", Double.class);

    private static <T> Object action(String str, Class<T> cls) {

        if (cls == String.class) {
            return str;
        } else if (cls == Double.class) {
            Double parseDouble = null;
            String chislo = str.replace("\"", "").replace(",", ".");
            try {
                parseDouble = Double.parseDouble(chislo);
            } catch (Exception ex) {
                String message = "ошибка парсинга вещественного числа! Ошибочное число - \"" + chislo + "\"";
                MOVEMENTS_LOG.error(message);
                System.err.println(message);
            }
            return parseDouble;
        }
        return null;
    }


    public Movements(String pathMovementsCsv) {

        Path path = Paths.get(pathMovementsCsv);

        if (Files.exists(path)) {
            loadFromFile(path);
        } else {
            String message = "Подгрузите файл \"" + path.getFileName() + "\" по пути " + path;
            MOVEMENTS_LOG.error(message);
            System.err.println(message);
        }

    }


    private void parseFileToStrArr(List<String> dump) {

        final String REGEX_CURRENT_RECORD = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try {

            final String[] headingTable = dump.get(0).split(REGEX_CURRENT_RECORD);
            final int colCount = headingTable.length;

            final Map<Integer, String> csvActualHeader = new HashMap<>();

            for (int i = 0; i < colCount; i++) {
                csvActualHeader.put(i, headingTable[i]);
            }


            for (int i = 1; i < dump.size(); i++) {


                ArrayList<String> columnList = new ArrayList<>(
                        Arrays.asList(
                                dump.get(i).split(REGEX_CURRENT_RECORD)
                        )
                );


                if (columnList.size() != colCount) {
                    String message = "Неверный формат строки, строка будет пропущена!\n\"" + dump.get(i) + "\"";
                    MOVEMENTS_LOG.info(message);
                    System.err.println(message);
                    continue;
                }

                Record record = new Record();

                for (int key : csvActualHeader.keySet()) {

                    Object actionValue = action(columnList.get(key), csvDefinition.get(csvActualHeader.get(key)));
                    String colTitle = csvActualHeader.get(key);

                    if (colTitle.matches("Приход|Расход")) {

                        Double currency = (Double) actionValue;

                        switch (colTitle) {
                            case "Приход":
                                record.setIncome(currency);
                                break;
                            case "Расход":
                                record.setExpense(currency);
                                break;
                        }

                    } else if (colTitle.matches("Описание операции")) {
                        record.setOrganizationPostingsName((String) actionValue);
                    }
                }

                recordsList.add(record);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            MOVEMENTS_LOG.error(message);
            System.err.println(message);
        }
    }

    private void loadFromFile(Path path) {
        try {
            parseFileToStrArr(Files.readAllLines(path));
        } catch (Exception ex) {
            String message = ex.getMessage();
            MOVEMENTS_LOG.error(message);
            System.err.println(message);
        }
    }


    public double getExpenseSum() {

        return recordsList.stream().mapToDouble(Record::getExpense).sum();
    }

    public double getIncomeSum() {

        return recordsList.stream().mapToDouble(Record::getIncome).sum();
    }

    public void getReport() {
        System.out.println("Сумма расходов: " + getExpenseSum() + " руб.");
        System.out.println("Сумма доходов: " + getIncomeSum() + " руб.");

        recordsList.stream()
                .collect(Collectors
                        .groupingBy(Record::getOrganizationPostingsName,
                                Collectors.summingDouble(Record::getExpense)))
                .forEach(

                        (s, aDouble) ->
                                System.out.println("Проводка организации \"" + s + "\":\n\tСумма расходов - " + aDouble + " руб.")
                );

        System.out.println("=============================================");
    }
}
