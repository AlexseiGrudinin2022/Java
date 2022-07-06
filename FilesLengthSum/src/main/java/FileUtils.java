import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;


public class FileUtils {

    private static long countFiles;
    static HashMap<Long, String> units;
    private static long countFilesFailed;

    private static final byte SPLIT = 3;
    private static final long UNIT = 1024;

    public static Logger logger = (Logger) LogManager.getRootLogger();


    static {
        units = new HashMap<>();
        units.put(UNIT, "KB");
        units.put(UNIT * UNIT, "MB");
        units.put(UNIT * UNIT * UNIT, "GB");
        units.put(UNIT * UNIT * UNIT * UNIT, "TB");
        units.put(UNIT * UNIT * UNIT * UNIT * UNIT, "PB");

        countFiles = 0;
        countFilesFailed = 0;
    }


    private static String stringFormatByte(final Long size) {

        String s = size.toString();

        StringBuilder tmp = new StringBuilder();

        char endDigit = s.charAt(s.length() - 1);

        for (int i = 0; i < s.length(); i += SPLIT) {

            if ((i + SPLIT) > s.length()) {
                tmp.append(s.substring(i));
                break;
            }
            tmp.append(s, i, i + SPLIT).append(" ");
        }

        String result = tmp.toString().trim() + " байт";

        final char FROM_RANGE = '2';
        final char TO_RANGE = '5';

        return (endDigit >= FROM_RANGE && endDigit < TO_RANGE) ? result + 'а' : result;

    }

    public static long calculateFolderSize(final String path, boolean showProcessScanFolder) {

        Path path_ = Paths.get(path);

        if (!Files.exists(path_)) {
            String errMsg = "Указанный \"" + path_ + "\" путь не найден!";
            System.err.println(errMsg);
            logger.error(errMsg);
            return -1;
        }

        try {
            SumSizeFilesOneDirectory check = new SumSizeFilesOneDirectory(showProcessScanFolder);
            Files.walkFileTree(path_, check);
            countFiles = check.getCountFiles();
            countFilesFailed = check.getCountFilesFailed();
            return check.getSumSizeFiles();
        } catch (Exception ex) {

            String errMsg = "Указанный \"" + path_ + "\" путь не найден!\nОшибка:" + ex.getMessage();
            System.err.println(errMsg);
            logger.error(errMsg);
            return -1;
        }

    }


    public static String showDirectorySizeForUnit(final Long size) {

        String result = "0.0 KB";

        ArrayList<Long> arr = new ArrayList<>(units.keySet());

        if (size >= UNIT) {
            Long key = arr.get(0);

            for (int i = 1; i < arr.size(); i++) {
                if (size > key && size < arr.get(i)) {
                    result = String.format("%.3f", size / (0.0 + key)) + " " + units.get(key);
                    break;
                }
                key = arr.get(i);
            }
        }

        return "Размер всех файлов: \n\t" + result + " (" +
                stringFormatByte(size) + ")" +
                "\nФайлов обработано: " + countFiles + "\n" +
                "Дистрибутивы с ошибкой: " + countFilesFailed + "\n";
    }

}






