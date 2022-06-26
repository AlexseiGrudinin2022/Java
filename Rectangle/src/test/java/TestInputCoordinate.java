import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rectangle.input.InputCoordinate;

import static org.junit.Assert.assertEquals;

@DisplayName("Тest")
public class TestInputCoordinate {
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
        Double actual = InputCoordinate.parseCoordinate("123.59");
        assertEquals(expected, actual);
    }




}