import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach // chạy trước từng test case một
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void addTwoPositiveNumbers() {
        int result = calculator.add(3, 4);
        assertEquals(7, result);
    }

    @Test
    void subtractReturnsCorrectValue() {
        assertEquals(5, calculator.subtract(10, 5));
    }

    @Test
    void divideByZeroThrowsException() {
        assertThrows(ArithmeticException.class,
                () -> calculator.divide(10, 0));
    }
}