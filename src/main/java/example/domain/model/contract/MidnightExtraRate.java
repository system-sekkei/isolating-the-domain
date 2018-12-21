package example.domain.model.contract;

/**
 * 深夜割増率
 */
public class MidnightExtraRate {
    ExtraPayRate value;

    public MidnightExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public MidnightHourlyExtraWage apply(HourlyWage hourlyWage) {
        return new MidnightHourlyExtraWage(value.apply(hourlyWage));
    }
}
