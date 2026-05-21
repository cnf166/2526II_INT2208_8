import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;

class ParameterizedDemo {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void isPositive(int number) {
        assertTrue(number > 0);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 7",
            "10, 5, 15",
            "-2, 2, 0"
    })
    void addReturnsSum(int a, int b, int expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.add(a, b));
    }
}