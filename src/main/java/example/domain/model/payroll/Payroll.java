package example.domain.model.payroll;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkTime;
import example.domain.model.contract.Contract;
import example.domain.model.contract.Contracts;
import example.domain.model.worker.Worker;

import java.math.BigDecimal;

/**
 * 給与
 */
public class Payroll {

    Worker worker;
    Contracts contracts;
    MonthlyAttendances monthlyAttendances;

    public Payroll(Worker worker, Contracts contracts, MonthlyAttendances monthlyAttendances) {
        this.worker = worker;
        this.contracts = contracts;
        this.monthlyAttendances = monthlyAttendances;
    }

    public Wage wage() {
        Wage wage = new Wage(BigDecimal.ZERO);
        for (Contract contract : contracts.list()) {
            WorkTime workTime = monthlyAttendances.workTimeWithin(contract.period());
            wage = wage.add(Wage.from(workTime, contract.wageCondition()));
        }
        return wage;
    }

    // TODO 時給登録無しの場合に備考に出力する何かしら
}
