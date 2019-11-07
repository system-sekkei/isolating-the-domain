package example.application.repository;


import example.domain.model.contract.ContractWages;
import example.domain.model.contract.Contracts;
import example.domain.model.wage.WageCondition;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.type.date.Date;

/**
 * 契約リポジトリ
 */
public interface ContractRepository {
    ContractWages getContractWages(Employee employee);

    void registerHourlyWage(Employee employee, Date effectiveDate, WageCondition wageCondition);

    Contracts findContracts(ContractingEmployees contractingEmployees);
}
