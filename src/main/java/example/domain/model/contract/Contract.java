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
        return period;
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage() {
        return hourlyWage().overTimeHourlyExtraWage(hourlyWageContract.overTimeExtraRate);
    }

    public MidnightHourlyExtraWage midnightHourlyExtraWage() {
        return hourlyWage().midnightHourlyExtraWage(hourlyWageContract.midnightExtraRate);
    }
}
