package example.domain.model.payroll;

import example.domain.type.date.Date;

import java.util.List;

/**
 * 月内の時給変遷
 */
public class MonthlyHourlyWages {
    List<DailyHourlyWage> wages;
    public MonthlyHourlyWages(List<DailyHourlyWage> wages) {
        this.wages = wages;
    }

    public HourlyWage get(Date date) {
        return wages.stream().filter(
                w -> w.date().value().equals(date.value())).findFirst().orElseThrow(() -> new RuntimeException()).hourlyWage();
    }
}
