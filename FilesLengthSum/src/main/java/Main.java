import java.io.IOException;
import java.util.Scanner;


public class Main {

    private static int countSearch = 1;

    public static void main(String[] args) throws IOException {

        System.out.println("Отображать процесс сканирования папки? ведите YES, либо, если нет - любые символы.");
        boolean showProcessScanFolder = (new Scanner(System.in)).nextLine().equalsIgnoreCase("yes");

        boolean isExit;
        do {
            System.out.println("Введите путь к дистрибутиву: ");
            String input = new Scanner(System.in).nextLine();
            isExit = input.equalsIgnoreCase("exit");
            if (!isExit) {
                outputResult(input, showProcessScanFolder);
            }
        } while (!isExit);
        System.out.println("Всего доброго!");

    }

    private static void outputResult(String path, boolean showProcessScanFolder) throws IOException {

        System.out.println("Прогон директории номер - " + countSearch);
        if (!path.isEmpty()) {
            System.out.println("Путь " + path);
        } else {
            System.out.println("Подсчет файлов в директории проекта");
        }

        long allFilesSize = FileUtils.calculateFolderSize(path, showProcessScanFolder);
        if (allFilesSize != -1) {
            System.out.println(FileUtils.showDirectorySizeForUnit(allFilesSize));
        }

        countSearch++;

    }
}

