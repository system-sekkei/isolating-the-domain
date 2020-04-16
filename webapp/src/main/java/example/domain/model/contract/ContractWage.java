package example.domain.model.contract;

import example.domain.model.wage.BaseHourlyWage;
import example.domain.type.date.Date;

/**
 * 契約給与
 */
public class ContractWage {
    ContractEffectiveDate effectiveDate;
    WageCondition wageCondition;

    @Deprecated
    public ContractWage() {
    }

    public ContractWage(ContractEffectiveDate effectiveDate, WageCondition wageCondition) {
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
