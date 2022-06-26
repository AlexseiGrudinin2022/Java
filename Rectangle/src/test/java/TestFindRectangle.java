import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rectangle.JSON.JSONWriter;
import rectangle.functional.ComputeOverLappingRectangle;
import rectangle.model.Rectangle;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestFindRectangle {

    private JSONWriter writer = new JSONWriter(Path.of("c:/result.json"));


    @Test
    @DisplayName("Rectangle is not null")
    void startComputeTest() {
        List<Rectangle> rectangleList = new ArrayList<>();


        rectangleList.add(new Rectangle(3.5, 7, 3, 5));
        rectangleList.add(new Rectangle(5.5, 8, 4.5, 7));

        boolean actual = new ComputeOverLappingRectangle(rectangleList, Path.of("c:/result.json")).compute("rects");
        boolean expected = true;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Rectangle is null")
    void startComputeTestForNull() {
        List<Rectangle> rectangleList = new ArrayList<>();
        rectangleList.add(new Rectangle(2.5, 5, 9, 10.5));
        rectangleList.add(new Rectangle(5.5, 8.5, 8.5, 10));
        boolean actual = new ComputeOverLappingRectangle(rectangleList, Path.of("c:/result.json")).compute("rects");

        boolean expected = false;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Add one rectangle")
    void addOneRectangle() {
        List<Rectangle> rectangleList = new ArrayList<>();
        rectangleList.add(new Rectangle(2.5, 5, 9, 10.5));
        boolean actual = new ComputeOverLappingRectangle(rectangleList, Path.of("c:/result.json")).compute("rects");
        boolean expected = false;
        Assertions.assertEquals(expected, actual);

    }
}
