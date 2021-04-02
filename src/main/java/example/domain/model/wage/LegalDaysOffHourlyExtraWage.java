package example.domain.model.wage;

/**
 * 法定休日労働 時給割増額
 */
public class LegalDaysOffHourlyExtraWage {
    HourlyWage value;

    public LegalDaysOffHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
