package example.domain.model.payroll;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkTime;
import example.domain.model.contract.Contract;
import example.domain.model.contract.WorkerContract;

import java.math.BigDecimal;

/**
 * 給与
 */
public class Payroll {

    WorkerContract workerContract;
    MonthlyAttendances monthlyAttendances;

    public Payroll(WorkerContract workerContract, MonthlyAttendances monthlyAttendances) {
        this.workerContract = workerContract;
        this.monthlyAttendances = monthlyAttendances;
    }

    public Wage wage() {
        if (payrollStatus().available()) {
            return Wage.invalid();
        }

        Wage wage = new Wage(BigDecimal.ZERO);
        for (Contract contract : workerContract.listContracts()) {
            WorkTime workTime = monthlyAttendances.workTimeWithin(contract.period());
            wage = wage.add(Wage.from(workTime, contract.wageCondition()));
        }
        return wage;
    }

    public PayrollStatus payrollStatus() {
        if (monthlyAttendances.notWorking()) {
            return PayrollStatus.稼働登録無し;
        }
        if (workerContract.notContractedAt(monthlyAttendances.firstWorkDay().value())) {
            return PayrollStatus.時給登録無し;
        }

        return PayrollStatus.有効;
    }
}
