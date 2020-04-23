package example.domain.model.wage;

/**
 * 深夜時給割増額
 */
// TODO: 削除予定
public class OverTimeHourlyExtraWage {
    HourlyWage value;

    public OverTimeHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }

    public HourlyWage value() {
        return value;
    }
}
