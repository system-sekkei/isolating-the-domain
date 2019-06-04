package example.application.repository;


import example.domain.model.contract.ContractWages;
import example.domain.model.contract.Contracts;
import example.domain.model.wage.WageCondition;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;

/**
 * 契約リポジトリ
 */
public interface ContractRepository {
    ContractWages getContractWages(EmployeeNumber employeeNumber);

    void registerHourlyWage(EmployeeNumber employeeNumber, Date startDate, WageCondition wageCondition);

    Contracts findContracts(ContractingEmployees contractingEmployees);
}
