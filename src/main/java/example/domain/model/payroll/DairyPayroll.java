package example.domain.model.payroll;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.contract.HourlyWage;
import example.domain.model.labour_standards_law.ExtraPay;
import example.domain.type.date.Date;
import example.domain.type.time.HourAndMinute;

import java.math.BigDecimal;

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
        Wage wage = Wage.of(attendanceOfDay.workTime(), hourlyWage);
        wage = wage.add(Wage.of(attendanceOfDay.overTime(), hourlyWage.withExtraRate(ExtraPay.時間外.leastExtraPayRate())));
        wage = wage.add(Wage.of(attendanceOfDay.midnightWorkTime(), hourlyWage.withExtraRate(ExtraPay.深夜.leastExtraPayRate())));
        return wage;
    }
}
