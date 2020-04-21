package example.domain.model.contract.wage;

import example.domain.model.wage.NightHourlyExtraWage;
import example.domain.model.wage.OverTimeHourlyExtraWage;

/**
 * 給与条件
 */
public class WageCondition {
    BaseHourlyWage baseHourlyWage;
    OverTimeExtraRate overTimeExtraRate;

    @Deprecated
    public WageCondition() {
    }

    public WageCondition(BaseHourlyWage baseHourlyWage, OverTimeExtraRate overTimeExtraRate) {
        this.baseHourlyWage = baseHourlyWage;
        this.overTimeExtraRate = overTimeExtraRate;
    }

    public WageCondition(BaseHourlyWage baseHourlyWage) {
        this(baseHourlyWage, OverTimeExtraRate.regulation());
    }

    public BaseHourlyWage baseHourlyWage() {
        return baseHourlyWage;
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return baseHourlyWage.overTimeHourlyExtraWage(overTimeExtraRate.overLegalTimeExtraRate());
    }

    public NightHourlyExtraWage nightHourlyExtraWage() {
        return baseHourlyWage.nightHourlyExtraWage(overTimeExtraRate.nightExtraRate());
    }
}
