package example.domain.model.payroll;

import example.domain.model.attendance.Attendances;
import example.domain.model.contract.Contract;
import example.domain.model.labour_standards_law.ExtraPay;

public class ContractPayroll {
    Contract contract;
    Attendances attendances;

    public ContractPayroll(Contract contract, Attendances attendances) {
        this.contract = contract;
        this.attendances = attendances;
    }

    public Wage wage() {
        //FIXME 法定休日判定
        Wage wage = Wage.of(WorkHours.of(attendances.workTime()), contract.hourlyWage());
        wage = wage.add(Wage.of(WorkHours.of(attendances.overTime()), contract.overTimeHourlyWage()));
        wage = wage.add(Wage.of(WorkHours.of(attendances.midnightWorkTime()), contract.midnightExtraPayRate()));
        return wage;
    }
}
