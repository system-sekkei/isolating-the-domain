package example.domain.model.contract;

import example.domain.model.contract.daysoff.ContractDaysOff;
import example.domain.model.contract.hours.ContractBreakTime;
import example.domain.model.contract.wage.BaseHourlyWage;
import example.domain.model.contract.wage.WageCondition;
import example.domain.type.date.Date;

/**
 * 労働条件
 */
public class ContractCondition {
    ContractEffectiveDate effectiveDate;
    WageCondition wageCondition;
    ContractBreakTime contractBreakTime;
    ContractDaysOff contractDaysOff;

    @Deprecated
    public ContractCondition() {
    }

    public ContractCondition(ContractEffectiveDate effectiveDate, WageCondition wageCondition) {
        this.effectiveDate = effectiveDate;
        this.wageCondition = wageCondition;
    }

    public WageCondition wageCondition() {
        return wageCondition;
    }

    public BaseHourlyWage baseHourlyWage() {
        return wageCondition.baseHourlyWage();
    }

    public ContractEffectiveDate effectiveDate() {
        return effectiveDate;
    }

    public boolean availableAt(Date date) {
        return effectiveDate.value().hasSameValue(date) || date.isAfter(effectiveDate.value());
    }
}
