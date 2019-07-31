package example.domain.model.contract;

import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.type.date.Date;

/**
 * 契約給与
 */
public class ContractWage {
    ContractStartingDate startDate;
    WageCondition wageCondition;

    public ContractWage(ContractStartingDate startDate, WageCondition wageCondition) {
        this.startDate = startDate;
        this.wageCondition = wageCondition;
    }

    public WageCondition wageCondition() {
        return wageCondition;
    }

    public HourlyWage hourlyWage() {
        return wageCondition.baseHourlyWage();
    }

    public ContractStartingDate startDate() {
        return startDate;
    }

    public boolean availableAt(Date date) {
        return startDate.value().hasSameValue(date) || date.isAfter(startDate.value());
    }
}
