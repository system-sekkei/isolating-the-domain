package example.application.repository;


import example.domain.model.contract.Contracts;
import example.domain.model.contract.EmploymentContracts;
import example.domain.model.contract.WageCondition;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;

/**
 * 契約リポジトリ
 */
public interface ContractRepository {
    Contracts getContracts(EmployeeNumber employeeNumber);

    void registerHourlyWage(EmployeeNumber employeeNumber, Date startDate, WageCondition wageCondition);

    EmploymentContracts findEmploymentContracts(ContractingEmployees contractingEmployees);
}
