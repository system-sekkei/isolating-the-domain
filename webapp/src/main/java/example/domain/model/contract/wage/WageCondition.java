package example.domain.model.contract.wage;

import example.domain.model.wage.*;

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

    // TODO: 削除予定
    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return new OverTimeHourlyExtraWage(baseHourlyWage.withExtraPayRate(overTimeExtraRate.overLegalTimeExtraRate().value()));
    }

    public NightHourlyExtraWage nightHourlyExtraWage() {
        return new NightHourlyExtraWage(baseHourlyWage.withExtraPayRate(overTimeExtraRate.nightExtraRate().value()));
    }

    public LegalDaysOffHourlyExtraWage legalDaysOffHourlyExtraWage() {
        return new LegalDaysOffHourlyExtraWage(baseHourlyWage.withExtraPayRate(overTimeExtraRate.legalDaysOffExtraRate().value()));
    }

    public OverLegalMoreThan60HoursHourlyExtraWage overLegalMoreThan60HoursHourlyExtraWage() {
        return new OverLegalMoreThan60HoursHourlyExtraWage(baseHourlyWage.withExtraPayRate(overTimeExtraRate.overLegalMoreThan60HoursExtraRate().value()));
    }

    public OverLegalUpTo60HoursHourlyExtraWage overLegalUpTo60HoursHourlyExtraWage() {
        return new OverLegalUpTo60HoursHourlyExtraWage(baseHourlyWage.withExtraPayRate(overTimeExtraRate.overLegalUpTo60HoursExtraRate().value()));
    }

}
