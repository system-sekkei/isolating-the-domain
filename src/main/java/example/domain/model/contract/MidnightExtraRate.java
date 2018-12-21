package example.domain.model.contract;

/**
 * 深夜割増率
 */
public class MidnightExtraRate {
    ExtraPayRate value;

    public MidnightExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public HourlyWage apply(HourlyWage hourlyWage) {
        return value.apply(hourlyWage);
    }
}
