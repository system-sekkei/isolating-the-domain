package example.domain.model.contract;

/**
 * 深夜時給割増額
 */
public class OverTimeHourlyExtraWage {
    private final HourlyWage value;

    public OverTimeHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
