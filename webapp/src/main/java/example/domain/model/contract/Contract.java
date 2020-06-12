package example.domain.model.contract;

import example.domain.model.contract.wage.BaseHourlyWage;
import example.domain.model.contract.wage.WageCondition;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;
import example.domain.type.date.Dates;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 従業員契約
 */
public class Contract {
    Employee employee;
    ContractConditions contractConditions;

    public Contract(Employee employee, ContractConditions contractConditions) {
        this.employee = employee;
        this.contractConditions = contractConditions;
    }

    public EmployeeNumber employeeNumber() {
        return employee.employeeNumber();
    }

    public Name employeeName() {
        return employee.name();
    }

    public ContractEffectiveDate contractStartingDate() {
        ArrayList<ContractCondition> list = new ArrayList<>(contractConditions.list());
        if (list.isEmpty()) {
            return ContractEffectiveDate.none();
        }
        return list.get(list.size() - 1).effectiveDate();
    }

    public BaseHourlyWage todayHourlyWage() {
        LocalDate today = LocalDate.now();
        if (contractStatus(today).disable()) {
            return BaseHourlyWage.disable();
        }
        return availableContractAt(today).baseHourlyWage();
    }

    public ContractCondition availableContractAt(LocalDate date) {
        return contractConditions.list().stream()
                .filter(contractCondition -> contractCondition.availableAt(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(date.toString()));
    }

    public ContractStatus contractStatus(LocalDate date) {
        return contractStartingDate().isAfter(date) ? ContractStatus.契約なし : ContractStatus.契約あり;
    }

    public ContractStatus contractStatus(Dates dates) {
        if (dates.isEmpty()) {
            return ContractStatus.判定不能;
        }
        return contractStatus(dates.first());
    }

    public WageCondition wageConditionAt(LocalDate date) {
        ContractCondition contractCondition = availableContractAt(date);
        return contractCondition.wageCondition();
    }
}
