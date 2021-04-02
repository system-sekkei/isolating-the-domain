package example.domain.model.wage;

/**
 * 法定時間外労働 月60時間以内 時給割増額
 */
public class OverLegalWithin60HoursHourlyExtraWage {
    HourlyWage value;

    public OverLegalWithin60HoursHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
