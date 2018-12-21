package example.domain.model.payroll;

import example.domain.model.attendance.Attendances;
import example.domain.model.attendance.WorkTime;
import example.domain.model.contract.Contract;

/**
 * 契約給与
 */
public class ContractPayroll {
    Contract contract;
    Attendances attendances;

    public ContractPayroll(Contract contract, Attendances attendances) {
        this.contract = contract;
        this.attendances = attendances;
    }

    public Wage wage() {
        WorkTime workTime = attendances.summarize();

        return Wage.of(WorkHours.of(workTime.totalWorkTime()), contract.hourlyWage())
                .add(workTime.overTime(), contract.overTimeHourlyExtraWage())
                .add(workTime.midnightWorkTime(), contract.midnightHourlyExtraWage());
    }
}
