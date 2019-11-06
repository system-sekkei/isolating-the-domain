package example.domain.model.wage;

import example.domain.model.legislation.NightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;

/**
 * 給与条件
 */
public class WageCondition {
    HourlyWage baseHourlyWage;
    OverTimeExtraRate overTimeExtraRate;
    NightExtraRate nightExtraRate;

    @Deprecated
    public WageCondition() {
    }

    public WageCondition(HourlyWage baseHourlyWage, OverTimeExtraRate overTimeExtraRate, NightExtraRate nightExtraRate) {
        this.baseHourlyWage = baseHourlyWage;
        this.overTimeExtraRate = overTimeExtraRate;
        this.nightExtraRate = nightExtraRate;
    }

    public WageCondition(HourlyWage baseHourlyWage) {
        this(baseHourlyWage, OverTimeExtraRate.legal(), NightExtraRate.legal());
    }

    public HourlyWage baseHourlyWage() {
        return baseHourlyWage;
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return baseHourlyWage.overTimeHourlyExtraWage(overTimeExtraRate);
    }

    public NightHourlyExtraWage nightHourlyExtraWage() {
        return baseHourlyWage.nightHourlyExtraWage(nightExtraRate);
    }
}
