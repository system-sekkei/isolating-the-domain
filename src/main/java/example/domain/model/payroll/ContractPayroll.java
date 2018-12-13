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

        //FIXME 法定休日判定
        Wage wage = Wage.of(WorkHours.of(workTime.totalWorkTime()), contract.hourlyWage());
        wage = wage.add(Wage.of(WorkHours.of(workTime.overTime()), contract.overTimeHourlyWage()));
        wage = wage.add(Wage.of(WorkHours.of(workTime.midnightWorkTime()), contract.midnightExtraPayRate()));
        return wage;
    }
}
