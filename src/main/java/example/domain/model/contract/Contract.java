package example.domain.model.contract;

import example.domain.type.date.Date;

/**
 * 雇用契約
 */
public class Contract {
    Date startDate;
    WageCondition wageCondition;

    public Contract(Date startDate, WageCondition wageCondition) {
        this.startDate = startDate;
        this.wageCondition = wageCondition;
    }

    public WageCondition wageCondition() {
        return wageCondition;
    }

    public HourlyWage hourlyWage() {
        return wageCondition.baseHourlyWage();
    }

    public Date startDate() {
        return startDate;
    }

    public boolean availableAt(Date date) {
        return startDate.hasSameValue(date) || date.isAfter(startDate);
    }
}
