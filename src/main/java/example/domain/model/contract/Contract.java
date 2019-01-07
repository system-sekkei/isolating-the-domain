package example.domain.model.contract;

import example.domain.type.date.Date;

/**
 * 雇用契約
 */
public class Contract {
    ContractStartingDate startDate;
    WageCondition wageCondition;

    public Contract(ContractStartingDate startDate, WageCondition wageCondition) {
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
