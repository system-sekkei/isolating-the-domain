package example.domain.model.contract;

/**
 * 時給契約
 */
public class HourlyWageContract {
    HourlyWage hourlyWage;
    OverTimeExtraRate overTimeExtraRate;
    MidnightExtraRate midnightExtraRate;

    public HourlyWageContract(HourlyWage hourlyWage, OverTimeExtraRate overTimeExtraRate, MidnightExtraRate midnightExtraRate) {
        this.hourlyWage = hourlyWage;
        this.overTimeExtraRate = overTimeExtraRate;
        this.midnightExtraRate = midnightExtraRate;
    }
}
