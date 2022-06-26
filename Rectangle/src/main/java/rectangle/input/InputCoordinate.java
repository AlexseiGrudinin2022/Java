package rectangle.input;

public class InputCoordinate {

    public static Double parseCoordinate(String coordinate) {

        try {
            return Double.parseDouble(coordinate.replaceAll("\\s+", ""));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
