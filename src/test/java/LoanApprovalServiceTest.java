import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LoanApprovalServiceTest {

    static Stream<Arguments> invalidCases() {
        return Stream.of(
                // TC01: age < 18
                Arguments.of(17, 20.0, 600, 'C'),
                // TC02: age > 65
                Arguments.of(66, 20.0, 600, 'C'),
                // TC03: income < 5.0
                Arguments.of(30, 4.9, 600, 'C'),
                // TC04: income > 500.0
                Arguments.of(30, 500.1, 600, 'C'),
                // TC05: credit_score < 300
                Arguments.of(30, 20.0, 299, 'C'),
                // TC06: credit_score > 850
                Arguments.of(30, 20.0, 851, 'C'),
                // TC07: invalid employment
                Arguments.of(30, 20.0, 600, 'X')
        );
    }

    static Stream<Arguments> validCases() {
        return Stream.of(
                // TC08 (QT 1): High Risk -> REJECT
                Arguments.of(18, 500.0, 300, 'F', Decision.REJECT),
                // TC09 (QT 2): Medium Risk + income < 15.0 -> REJECT
                Arguments.of(65, 14.9, 501, 'C', Decision.REJECT),
                // TC10 (QT 3): Low Risk + income < 15.0 + Contract -> MANUAL REVIEW
                Arguments.of(40, 5.0, 701, 'C', Decision.MANUAL_REVIEW),
                // TC11 (QT 4): Low Risk + income < 15.0 + Freelance -> REJECT
                Arguments.of(40, 14.9, 850, 'F', Decision.REJECT),
                // TC12 (QT 5): (Low/Med) + income >= 15.0 + Contract -> APPROVE
                Arguments.of(40, 15.0, 700, 'C', Decision.APPROVE),
                // TC13 (QT 6): (Low/Med) + income >= 15.0 + Freelance -> MANUAL REVIEW
                Arguments.of(40, 500.0, 701, 'F', Decision.MANUAL_REVIEW)
        );
    }

    @DisplayName("Invalid input should throw IllegalArgumentException(\"Invalid Input\")")
    @ParameterizedTest(name = "age={0}, income={1}, creditScore={2}, employment={3}")
    @MethodSource("invalidCases")
    void invalidInputThrows(int age, double income, int creditScore, char employment) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> LoanApprovalService.evaluate(age, income, creditScore, employment)
        );
        assertEquals("Invalid Input", ex.getMessage());
    }

    @DisplayName("Valid input should follow decision rules")
    @ParameterizedTest(name = "age={0}, income={1}, creditScore={2}, employment={3} -> {4}")
    @MethodSource("validCases")
    void validInputsReturnExpectedDecision(int age, double income, int creditScore, char employment, Decision expected) {
        assertEquals(expected, LoanApprovalService.evaluate(age, income, creditScore, employment));
    }
}
