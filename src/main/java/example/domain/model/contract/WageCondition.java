package example.domain.model.contract;

import example.domain.model.labour_standards_law.MidnightExtraRate;
import example.domain.model.labour_standards_law.OverTimeExtraRate;

/**
 * 給与条件
 */
public class WageCondition {
    HourlyWage baseHourlyWage;
    OverTimeExtraRate overTimeExtraRate;
    MidnightExtraRate midnightExtraRate;

    public WageCondition(HourlyWage baseHourlyWage, OverTimeExtraRate overTimeExtraRate, MidnightExtraRate midnightExtraRate) {
        this.baseHourlyWage = baseHourlyWage;
        this.overTimeExtraRate = overTimeExtraRate;
        this.midnightExtraRate = midnightExtraRate;
    }

    public WageCondition(HourlyWage baseHourlyWage) {
        this(baseHourlyWage, OverTimeExtraRate.legal(), MidnightExtraRate.legal());
    }

    public HourlyWage baseHourlyWage() {
        return baseHourlyWage;
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return baseHourlyWage.overTimeHourlyExtraWage(overTimeExtraRate);
    }

    public MidnightHourlyExtraWage midnightHourlyExtraWage() {
        return baseHourlyWage.midnightHourlyExtraWage(midnightExtraRate);
    }
}
