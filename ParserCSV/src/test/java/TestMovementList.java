import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Файл test/resources/movementList.csv")
public class TestMovementList {

    private static final double DELTA = 0.01;
    private static final String CSV_FILENAME = "movementList.csv";

    @Test
    @DisplayName("Сумма прихода")
    void testSumIncome(){
        assertEquals(459300.0, new Movements(getCsvFilenamePath()).getIncomeSum(), DELTA);
    }

    @Test
    @DisplayName("Сумма расходов")
    void testSumExpense()  {
        assertEquals(464216.57, new Movements(getCsvFilenamePath()).getExpenseSum(), DELTA);
    }

    private String getCsvFilenamePath() {
        return new File(this.getClass().getResource(CSV_FILENAME).getPath()).getAbsolutePath();
    }

}