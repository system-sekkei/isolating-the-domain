package example.domain.model.wage;

/**
 * 法定外休日労働 時給割増額
 */
public class NonLegalDaysOffHourlyExtraWage {
    HourlyWage value;

    public NonLegalDaysOffHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
