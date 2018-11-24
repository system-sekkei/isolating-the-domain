package example.domain.model.payroll;

import example.domain.model.attendance.Attendance;
import example.domain.model.contract.ExtraPayRate;
import example.domain.model.contract.HourlyWage;
import example.domain.type.date.Date;

/**
 * 日毎給与
 */
public class DairyPayroll {
    Date date;
    Attendance attendance;
    HourlyWage hourlyWage;

    public DairyPayroll(Date date, Attendance attendance, HourlyWage hourlyWage) {
        this.date = date;
        this.attendance = attendance;
        this.hourlyWage = hourlyWage;
    }

    Wage wage() {
        //FIXME 法定休日判定
        Wage wage = Wage.of(WorkHours.of(attendance.workTime()), hourlyWage);
        wage = wage.add(Wage.of(WorkHours.of(attendance.overTime()), hourlyWage.withExtraRate(new ExtraPayRate("0.25"))));
        wage = wage.add(Wage.of(WorkHours.of(attendance.midnightWorkTime()), hourlyWage.withExtraRate(new ExtraPayRate("0.25"))));
        return wage;
    }
}
