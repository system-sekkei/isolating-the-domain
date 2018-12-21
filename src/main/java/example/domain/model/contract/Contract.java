package example.domain.model.contract;

import example.domain.type.date.Date;
import example.domain.type.date.DateRange;

/**
 * 雇用契約
 */
public class Contract {
    DateRange period;

    HourlyWageContract hourlyWageContract;

    public Contract(Date startDate, Date endDate, HourlyWageContract hourlyWageContract) {
        this.period = new DateRange(startDate, endDate);
        this.hourlyWageContract = hourlyWageContract;
    }

    public HourlyWage hourlyWage() {
        return hourlyWageContract.hourlyWage;
    }

    public Date startDate() {
        return period.startDate();
    }

    public Date endDate() {
        return period.endDate();
    }

    public DateRange period() {
        return new DateRange(startDate());
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return hourlyWageContract.overTimeExtraRate.apply(hourlyWage());
    }

    public MidnightHourlyExtraWage midnightHourlyExtraWage() {
        return hourlyWageContract.midnightExtraRate.apply(hourlyWage());
    }
}
