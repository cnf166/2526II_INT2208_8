public final class LoanApprovalService {

    private LoanApprovalService() {
    }

    public static Decision evaluate(int age, double income, int creditScore, char employment) {
        validate(age, income, creditScore, employment);

        RiskLevel risk = riskLevelOf(creditScore);
        // risk cao --> reject luôn (QT1)
        if (risk == RiskLevel.HIGH) {
            return Decision.REJECT;
        }

        // base đều là < 15 income
        //income < 15 và rík medium --> reject luôn bất kể tình trạng công việc (QT2)

        if (income < 15.0) {
            if (employment == 'F') {
                return Decision.REJECT; // QT4
            }
            if (risk == RiskLevel.MEDIUM) {
                return Decision.REJECT; // QT3
            }
            return Decision.MANUAL_REVIEW; // Low risk + Contract (
        }
        // QT5 + 6
        if (employment == 'C') {
            return Decision.APPROVE; // Medium/Low + >=15.0 + Contract
        }
        return Decision.MANUAL_REVIEW; // Medium/Low + >=15.0 + Freelance
    }

    private static void validate(int age, double income, int creditScore, char employment) {
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Invalid Input");
        }
        if (Double.isNaN(income) || Double.isInfinite(income)) {
            throw new IllegalArgumentException("Invalid Input");
        }
        if (income < 5.0 || income > 500.0) {
            throw new IllegalArgumentException("Invalid Input");
        }
        if (!hasExactlyOneDecimalPlace(income)) {
            throw new IllegalArgumentException("Invalid Input");
        }
        if (creditScore < 300 || creditScore > 850) {
            throw new IllegalArgumentException("Invalid Input");
        }
        if (employment != 'C' && employment != 'F') {
            throw new IllegalArgumentException("Invalid Input");
        }
    }

    static RiskLevel riskLevelOf(int creditScore) {
        if (creditScore <= 500) {
            return RiskLevel.HIGH;
        }
        if (creditScore <= 700) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }

    private static boolean hasExactlyOneDecimalPlace(double value) {
        double scaled = value * 10.0;
        long rounded = Math.round(scaled);
        return Math.abs(scaled - rounded) < 1e-9;
    }
}
