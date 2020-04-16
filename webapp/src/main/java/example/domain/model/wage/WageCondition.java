package example.domain.model.wage;

import example.domain.model.contract.*;

/**
 * 給与条件
 */
public class WageCondition {
    HourlyWage baseHourlyWage;
    WithinLegalOverTimeExtraRate withinLegalOverTimeExtraRate;
    OverLegalTimeExtraRate overLegalTimeExtraRate;
    PrescribedDaysOffExtraRate prescribedDaysOffExtraRate;
    LegalDaysOffExtraRate legalDaysOffExtraRate;
    OverTime60HoursPerMonthExtraRate overTime60HoursPerMonthExtraRate;
    NightExtraRate nightExtraRate;

    @Deprecated
    public WageCondition() {
    }

    public WageCondition(HourlyWage baseHourlyWage, OverLegalTimeExtraRate overLegalTimeExtraRate, NightExtraRate nightExtraRate) {
        this.baseHourlyWage = baseHourlyWage;
        this.overLegalTimeExtraRate = overLegalTimeExtraRate;
        this.nightExtraRate = nightExtraRate;
    }

    public WageCondition(HourlyWage baseHourlyWage) {
        this(baseHourlyWage, OverLegalTimeExtraRate.legal(), NightExtraRate.legal());
    }

    public HourlyWage baseHourlyWage() {
        return baseHourlyWage;
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return baseHourlyWage.overTimeHourlyExtraWage(overLegalTimeExtraRate);
    }

    public NightHourlyExtraWage nightHourlyExtraWage() {
        return baseHourlyWage.nightHourlyExtraWage(nightExtraRate);
    }
}
