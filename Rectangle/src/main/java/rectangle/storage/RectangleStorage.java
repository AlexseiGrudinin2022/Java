package rectangle.storage;

import rectangle.model.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RectangleStorage {


    private static ArrayList<Rectangle> rectangles = new ArrayList<>();

    public static ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public static void addRectangle(Rectangle rectangle) {
        rectangles.add(rectangle);
    }

    public static void addRectangleList(List<Rectangle> rectangleList) {
        rectangles.addAll(rectangleList);
    }


    public static void clearStorage() {
        rectangles.clear();
    }
}
