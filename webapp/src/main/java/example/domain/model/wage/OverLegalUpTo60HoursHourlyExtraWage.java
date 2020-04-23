package example.domain.model.wage;

/**
 * 所定時間外 法定超 月60時間以内 時給割増額
 */
public class OverLegalUpTo60HoursHourlyExtraWage {
    HourlyWage value;

    public OverLegalUpTo60HoursHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }
}
