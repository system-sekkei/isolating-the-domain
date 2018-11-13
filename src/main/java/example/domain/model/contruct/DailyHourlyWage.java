package example.domain.model.contruct;

import example.domain.model.contruct.HourlyWage;
import example.domain.type.date.Date;

/**
 * 日の時給
 */
public class DailyHourlyWage {
    Date date;
    HourlyWage hourlyWage;
    public DailyHourlyWage(Date date, HourlyWage hourlyWage) {
        this.date = date;
        this.hourlyWage = hourlyWage;
    }

    public Date date() {
        return date;
    }

    public HourlyWage hourlyWage() {
        return hourlyWage;
    }
}
