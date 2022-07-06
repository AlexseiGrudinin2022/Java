import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public final class PrintMapOfCite {

    private final Set<String> resultSet;

    public PrintMapOfCite(final String root) {
        System.out.println("Выполняется обработка сайта...("+new Date()+")");
        resultSet = new ForkJoinPool().invoke(new LinkNode(root));
    }


    private int computeLevel(String link) {
        //Если количество отрицательных слешей меньше нуля, то будет 0,
        // или в ссылке нет слешей, то тоже 0
        int COUNT_HTTP_AND_END_COUNT_SLASH = 3;
        return
                Math.toIntExact((link.isEmpty()) ? 0 : (Math.max((link
                        .trim()
                        .chars()
                        .filter(f -> f == '/')
                        .count() - COUNT_HTTP_AND_END_COUNT_SLASH), 0)));
    }

    private String tabulations(int count) {
        return "\t".repeat(count);
    }

    public void saveMapOfSiteFromFile(String path) {

        System.out.println("Подождите, составляем карту сайта!\n");

        try (FileWriter fileWriter = new FileWriter(path)) {
            resultSet.forEach(str -> {
                try {
                    String result = tabulations(computeLevel(str)) + str + "\r";
                    fileWriter.write(result+"\n");
                    fileWriter.flush();
                    System.out.println("\tВ файл записана строка: "+result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("\nВыполнено успешно! (" + new Date()+")");

        } catch (Exception ex) {
            System.err.println("Что-то пошло не так...");

        }

    }
}
