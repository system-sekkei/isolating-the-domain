package example.domain.model.wage;

/**
 * 深夜 時給割増額
 */
public class NightHourlyExtraWage {
    HourlyWage value;

    public NightHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
