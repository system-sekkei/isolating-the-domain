package example.domain.model.payroll;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkHours;
import example.domain.model.labour_standards_law.ExtraPayRate;
import example.domain.model.worker.Worker;
import example.domain.type.date.YearMonth;

/**
 * 給与
 */
public class Payroll {
    Worker worker;
    MonthlyAttendances monthlyAttendances;
    MonthlyHourlyWages monthlyHourlyWages;

    public Payroll() {
        //FIXME ...
    }

    public Payroll(Worker worker, MonthlyAttendances monthlyAttendance, MonthlyHourlyWages monthlyHourlyWage) {
        this.worker = worker;
        this.monthlyAttendances = monthlyAttendance;
        this.monthlyHourlyWages = monthlyHourlyWage;
    }

    Wage wage() {
        // TODO ここでインスタンス生成してるものをよそから受け取る。コンストラクタ？引数？

        HourlyWage hourlyWage = new HourlyWage(1000);

        WorkHours totalWorkHours = monthlyAttendances.standardWorkHours();
        Wage normalWage = hourlyWage.calculateWage(totalWorkHours);

        WorkHours overtimeHours = monthlyAttendances.overtimeWorkHours();
        ExtraPayRate overtimePayRate = new ExtraPayRate("1.25");
        HourlyWage overtimeHourlyWage = hourlyWage.withExtraRate(overtimePayRate);
        Wage overtimeWage = overtimeHourlyWage.calculateWage(overtimeHours);


        WorkHours midnightWorkHours = monthlyAttendances.midnightWorkHours();
        ExtraPayRate midnightPayRate = new ExtraPayRate("1.35");
        HourlyWage midnightHourlyWage = hourlyWage.withExtraRate(midnightPayRate);
        Wage midnightWage = midnightHourlyWage.calculateWage(midnightWorkHours);

        // TODO 休日

        return normalWage
                .add(overtimeWage)
                .add(midnightWage);
    }
}
