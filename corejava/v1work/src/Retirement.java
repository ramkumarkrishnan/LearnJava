import java.time.LocalDate;

public class Retirement {

    private static final LocalDate fullRetirement_1955 = LocalDate.of(1955, 1, 1);
    private static final LocalDate fullRetirement_1960 = LocalDate.of(1960, 1, 1);

    public Retirement() {

    }

    public LocalDate getQualifyDateForSSChecks (LocalDate birthDay) {
        return LocalDate.MAX;
    }

    public double monthlySSCheckAmount (LocalDate birthDay) {
        return (double)0;
    }

    public int getYearsToFullRetirement (LocalDate birthDay) {
        return (int)0;
    }
}
