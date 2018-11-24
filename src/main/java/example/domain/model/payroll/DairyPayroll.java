package example.domain.model.payroll;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.contract.ExtraPayRate;
import example.domain.model.contract.HourlyWage;
import example.domain.type.date.Date;

/**
 * 日毎給与
 */
public class DairyPayroll {
    Date date;
    AttendanceOfDay attendanceOfDay;
    HourlyWage hourlyWage;

    public DairyPayroll(Date date, AttendanceOfDay attendanceOfDay, HourlyWage hourlyWage) {
        this.date = date;
        this.attendanceOfDay = attendanceOfDay;
        this.hourlyWage = hourlyWage;
    }

    Wage wage() {
        //FIXME 法定休日判定
        Wage wage = Wage.of(WorkHours.of(attendanceOfDay.workTime()), hourlyWage);
        wage = wage.add(Wage.of(WorkHours.of(attendanceOfDay.overTime()), hourlyWage.withExtraRate(new ExtraPayRate("0.25"))));
        wage = wage.add(Wage.of(WorkHours.of(attendanceOfDay.midnightWorkTime()), hourlyWage.withExtraRate(new ExtraPayRate("0.25"))));
        return wage;
    }
}
