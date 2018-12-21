package example.domain.model.contract;

import example.domain.type.date.Date;
import example.domain.type.date.DateRange;

/**
 * 雇用契約
 */
public class Contract {
    HourlyWage hourlyWage;
    DateRange period;

    public Contract(Date startDate, Date endDate, HourlyWage hourlyWage) {
        this.period = new DateRange(startDate, endDate);
        this.hourlyWage = hourlyWage;
    }

    public HourlyWage hourlyWage() {
        return hourlyWage;
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

    public OverTimeHourlyExtraWage overTimeAdditionalHourlyWage() {
        return new OverTimeExtraRate(25).apply(hourlyWage());
    }

    public MidnightHourlyExtraWage midnightAdditionalHourlyWage() {
        return new MidnightExtraRate(35).apply(hourlyWage());
    }
}
