package example.domain.model.wage;

/**
 * 所定時間外 法定超 月60時間超 時給割増額
 */
public class OverLegalMoreThan60HoursHourlyExtraWage {
    HourlyWage value;

    public OverLegalMoreThan60HoursHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
