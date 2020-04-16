package example.domain.model.wage;

import example.domain.model.contract.*;

/**
 * 給与条件
 */
public class WageCondition {
    HourlyWage baseHourlyWage;
    OverTimeExtraRate overTimeExtraRate;

    @Deprecated
    public WageCondition() {
    }

    public WageCondition(HourlyWage baseHourlyWage, OverTimeExtraRate overTimeExtraRate) {
        this.baseHourlyWage = baseHourlyWage;
        this.overTimeExtraRate = overTimeExtraRate;
    }

    public WageCondition(HourlyWage baseHourlyWage) {
        // FIXME:
        this(baseHourlyWage, new OverTimeExtraRate(null, OverLegalTimeExtraRate.legal(), null, null, null, NightExtraRate.legal()));
    }

    public HourlyWage baseHourlyWage() {
        return baseHourlyWage;
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return baseHourlyWage.overTimeHourlyExtraWage(overTimeExtraRate.overLegalTimeExtraRate());
    }

    public NightHourlyExtraWage nightHourlyExtraWage() {
        return baseHourlyWage.nightHourlyExtraWage(overTimeExtraRate.nightExtraRate());
    }
}
