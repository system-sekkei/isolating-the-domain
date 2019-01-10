package example.domain.model.contract;

import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;
import example.domain.type.date.Date;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 従業員契約
 */
public class EmploymentContract {
    Employee employee;
    Contracts contracts;

    public EmploymentContract(Employee employee, Contracts contracts) {
        this.employee = employee;
        this.contracts = contracts;
    }

    public EmployeeNumber employeeNumber() {
        return employee.employeeNumber();
    }

    public Name employeeName() {
        return employee.name();
    }

    public ContractStartingDate contractStartingDate() {
        ArrayList<Contract> list = new ArrayList<>(contracts.list());
        if (list.isEmpty()) {
            return ContractStartingDate.none();
        }
        return list.get(list.size() - 1).startDate();
    }

    public HourlyWage todayHourlyWage() {
        Date today = new Date(LocalDate.now());
        if (contractStatus(today).disable()) {
            return HourlyWage.disable();
        }
        return availableContractAt(today).hourlyWage();
    }

    public Contract availableContractAt(Date date) {
        return contracts.list().stream()
                .filter(contract -> contract.availableAt(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(date.toString()));
    }

    public ContractStatus contractStatus(Date value) {
        return contractStartingDate().isAfter(value) ? ContractStatus.契約なし : ContractStatus.契約あり;
    }
}
