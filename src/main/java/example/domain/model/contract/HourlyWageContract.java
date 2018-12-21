package example.domain.model.contract;

import example.domain.model.labour_standards_law.MidnightExtraRate;
import example.domain.model.labour_standards_law.OverTimeExtraRate;

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

    public HourlyWageContract(HourlyWage hourlyWage) {
        this(hourlyWage, OverTimeExtraRate.legal(), MidnightExtraRate.legal());
    }
}
