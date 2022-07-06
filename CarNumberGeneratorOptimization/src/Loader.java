import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Loader {

    final static int COUNT_NUMBERS_FROM_ONE_REGIONS = 999;
    final static int COUNT_PROCESSORS = Runtime.getRuntime().availableProcessors();
    final static char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        byte size = (byte) numberStr.length();
        return
                size < numberLength ? "0".repeat(numberLength - size).concat(numberStr) : numberStr;
    }


    private static long countAllNumbers(int countRegion) {
        final int COUNT_LETTERS_IN_NUMBER = 3;
        return (long) Math.pow(letters.length,
                COUNT_LETTERS_IN_NUMBER) *
                COUNT_NUMBERS_FROM_ONE_REGIONS *
                countRegion;
    }


    private static void runGeneration(int currentRegion) {

        StringBuilder carNumberCurrentRegions = new StringBuilder();
        String padNumberRegion = padNumber(currentRegion, 2);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("res/regions_" + padNumberRegion + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int number = 1; number <= COUNT_NUMBERS_FROM_ONE_REGIONS; number++) {
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {

                        carNumberCurrentRegions
                                .append(firstLetter)
                                .append(padNumber(number, 3))
                                .append(secondLetter)
                                .append(thirdLetter)
                                .append(padNumberRegion)
                                .append("\n");
                    }
                }
            }
        }

        writer.write(carNumberCurrentRegions.toString());

        writer.flush();
        writer.close();
    }

    private static void threadTerminate(ThreadPoolExecutor threadPoolExecutor, int secondOfTimeOut) {
        threadPoolExecutor.shutdown();

        try {
            threadPoolExecutor.awaitTermination(secondOfTimeOut, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        final int COUNT_REGIONS_GENERATION = 20;
        int countProcessorIsThread = (COUNT_PROCESSORS > 1) ? COUNT_PROCESSORS - 1 : 1;
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(countProcessorIsThread);

        long start = System.currentTimeMillis();

        for (var ref = new Object() {
            int reg = 1;
        }; ref.reg <= COUNT_REGIONS_GENERATION; ref.reg++) {
            threadPoolExecutor.submit(() ->
                    runGeneration(ref.reg));
        }

        threadTerminate(threadPoolExecutor, 150);


        System.out.println((System.currentTimeMillis() - start) + " ms");
        System.out.println("Всего номеров: " + countAllNumbers(COUNT_REGIONS_GENERATION));
    }


}
