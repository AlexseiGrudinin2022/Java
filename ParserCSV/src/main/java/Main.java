public class Main {

    private static final String pathMovementsCsv = "src\\test\\resources\\movementList.csv";
    private static final String pathMovementsFloatCsv = "src\\test\\resources\\movementListFloat.csv";
    private static final String pathMovementsIntegerCsv = "src\\test\\resources\\movementListInteger.csv";


    public static void main(String[] args) {
        Movements movements = new Movements(pathMovementsCsv);
        movements.getReport();
        Movements movementsFloat = new Movements(pathMovementsFloatCsv);
        movementsFloat.getReport();
        Movements movementsInteger = new Movements(pathMovementsIntegerCsv);
        movementsInteger.getReport();
    }
}
