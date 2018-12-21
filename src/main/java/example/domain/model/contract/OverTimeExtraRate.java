package example.domain.model.contract;

/**
 * 時間外割増率
 */
public class OverTimeExtraRate {
    ExtraPayRate value;

    public OverTimeExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public HourlyWage apply(HourlyWage hourlyWage) {
        return value.apply(hourlyWage);
    }
}
