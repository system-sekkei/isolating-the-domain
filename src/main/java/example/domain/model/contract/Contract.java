package example.domain.model.contract;

import example.domain.type.date.Date;
import example.domain.type.date.DateRange;

/**
 * 雇用契約
 */
public class Contract {
    DateRange period;
    WageCondition wageCondition;

    public Contract(DateRange period, WageCondition wageCondition) {
        this.period = period;
        this.wageCondition = wageCondition;
    }

    public WageCondition wageCondition() {
        return wageCondition;
    }

    public HourlyWage hourlyWage() {
        return wageCondition.baseHourlyWage();
    }

    public Date startDate() {
        return period.startDate();
    }

    public Date endDate() {
        return period.endDate();
    }

    public DateRange period() {
        return period;
    }
}
