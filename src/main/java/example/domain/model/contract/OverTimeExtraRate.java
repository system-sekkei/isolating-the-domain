package example.domain.model.contract;

/**
 * 時間外割増率
 */
public class OverTimeExtraRate {
    ExtraPayRate value;

    public OverTimeExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public OverTimeHourlyExtraWage apply(HourlyWage hourlyWage) {
        return new OverTimeHourlyExtraWage(value.apply(hourlyWage));
    }
}
