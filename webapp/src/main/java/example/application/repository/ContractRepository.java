package example.application.repository;


import example.domain.model.contract.ContractEffectiveDate;
import example.domain.model.contract.ContractWages;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.WageCondition;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;

/**
 * 契約リポジトリ
 */
public interface ContractRepository {
    ContractWages getContractWages(Employee employee);

    void registerHourlyWage(Employee employee, ContractEffectiveDate effectiveDate, WageCondition wageCondition);

    Contracts findContracts(ContractingEmployees contractingEmployees);
}
