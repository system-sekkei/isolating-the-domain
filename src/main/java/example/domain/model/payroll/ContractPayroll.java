package example.domain.model.payroll;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkTime;
import example.domain.model.contract.Contract;

/**
 * 契約給与
 */
public class ContractPayroll {
    Contract contract;
    MonthlyAttendances monthlyAttendances;

    public ContractPayroll(Contract contract, MonthlyAttendances monthlyAttendances) {
        this.contract = contract;
        this.monthlyAttendances = monthlyAttendances;
    }

    public Wage wage() {
        WorkTime workTime = monthlyAttendances.workTimeWithin(contract.period());

        return Wage.of(WorkHours.of(workTime.totalWorkTime()), contract.hourlyWage())
                .add(workTime.overTime(), contract.overTimeHourlyExtraWage())
                .add(workTime.midnightWorkTime(), contract.midnightHourlyExtraWage());
    }
}
