package example.domain.model.contract;

import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;
import example.domain.type.date.Date;
import example.domain.type.date.Dates;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 従業員契約
 */
public class Contract {
    Employee employee;
    ContractWages contractWages;
    ContractBreakTime contractBreakTime;
    ContractDaysOff contractDaysOff;

    public Contract(Employee employee, ContractWages contractWages) {
        this.employee = employee;
        this.contractWages = contractWages;
    }

    public EmployeeNumber employeeNumber() {
        return employee.employeeNumber();
    }

    public Name employeeName() {
        return employee.name();
    }

    public ContractEffectiveDate contractStartingDate() {
        ArrayList<ContractWage> list = new ArrayList<>(contractWages.list());
        if (list.isEmpty()) {
            return ContractEffectiveDate.none();
        }
        return list.get(list.size() - 1).effectiveDate();
    }

    public BaseHourlyWage todayHourlyWage() {
        Date today = new Date(LocalDate.now());
        if (contractStatus(today).disable()) {
            return BaseHourlyWage.disable();
        }
        return availableContractAt(today).baseHourlyWage();
    }

    public ContractWage availableContractAt(Date date) {
        return contractWages.list().stream()
                .filter(contractWage -> contractWage.availableAt(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(date.toString()));
    }

    public ContractStatus contractStatus(Date date) {
        return contractStartingDate().isAfter(date) ? ContractStatus.契約なし : ContractStatus.契約あり;
    }

    public ContractStatus contractStatus(Dates dates) {
        if (dates.isEmpty()) {
            return ContractStatus.判定不能;
        }
        return contractStatus(dates.first());
    }

    public WageCondition wageConditionAt(Date date) {
        ContractWage contractWage = availableContractAt(date);
        return contractWage.wageCondition();
    }
}
