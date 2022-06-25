import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rectangle.functional.ActionImpl;
import rectangle.input.InputCoordinate;
import rectangle.model.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DisplayName("Тest")
public class TestInputAndActionCompute {
    @Test
    @DisplayName("NullStringExpected")
    void nullStringExpectedForInput() {

        Double expected = null;
        Double actual = InputCoordinate.parseCoordinate("jhs///");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("emptyStringActual")
    void emptyStringActualForInput() {
        Double expected = null;
        Double actual = InputCoordinate.parseCoordinate("");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("validDump")
    void validDumpForInputAndReplace() {
        Double expected = 123.59;
        Double actual = InputCoordinate.parseCoordinate("123,59ppp");
        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Rectangle is not null")
    void startComputeTest() {
        List<Rectangle> rectangleList = new ArrayList<>();
        rectangleList.add(new Rectangle(3.5, 7, 3, 5));
        rectangleList.add(new Rectangle(5.5, 8, 4.5, 7));
        Rectangle actual = new ActionImpl(rectangleList).compute();
        Rectangle expected = new Rectangle(5.5, 7, 4.5, 5);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Rectangle is null")
    void startComputeTestForNull() {
        List<Rectangle> rectangleList = new ArrayList<>();
        rectangleList.add(new Rectangle(2.5, 5, 9, 10.5));
        rectangleList.add(new Rectangle(5.5, 8.5, 8.5, 10));
        Rectangle actual = new ActionImpl(rectangleList).compute();
        Rectangle expected = null;
        Assertions.assertEquals(expected, actual);

    }

}