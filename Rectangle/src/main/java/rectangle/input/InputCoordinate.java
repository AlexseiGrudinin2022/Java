package rectangle.input;

public class InputCoordinate {

    public static Double parseCoordinate(String coordinate) {

        String temp = coordinate
                .replaceAll("[^\\-\\d.\\,]+", "") // убираем все кроме цифр, точки и запятой
                .replaceAll(",", "."); //меняем запятую на точку

        if (temp.isEmpty()) {
            return null;
        }

        long countPointAndMinus = temp.chars().filter(f -> f == '-' || f == '.').count();

        if (temp.charAt(0) == '-') {
            countPointAndMinus--;
        }
        return (countPointAndMinus > 1) ? null : Double.parseDouble(temp);
    }



}
