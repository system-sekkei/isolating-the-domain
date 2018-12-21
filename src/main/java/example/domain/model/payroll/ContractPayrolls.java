package example.domain.model.payroll;

import example.domain.model.attendance.Attendances;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.contract.Contract;
import example.domain.model.contract.Contracts;

import java.util.ArrayList;
import java.util.List;

/**
 * 契約給与一覧
 */
public class ContractPayrolls {
    List<ContractPayroll> list;

    public ContractPayrolls(Contracts contracts, MonthlyAttendances monthlyAttendances) {
        this.list = new ArrayList<>();
        for (Contract contract : contracts.value()) {
            Attendances attendances = monthlyAttendances.attendancesOf(contract.period());
            ContractPayroll contractPayroll = new ContractPayroll(contract, attendances);
            list.add(contractPayroll);
        }
    }

    public Wage wage() {
        Wage wage = new Wage();
        for(ContractPayroll contractPayroll : list) {
            wage = wage.add(contractPayroll.wage());
        }
        return wage;
    }
}
