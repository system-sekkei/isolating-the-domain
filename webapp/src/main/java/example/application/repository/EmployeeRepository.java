package example.application.repository;

import example.domain.model.employee.*;

/**
 * 従業員リポジトリ
 */
public interface EmployeeRepository {
    Employee choose(EmployeeNumber employeeNumber);

    ContractingEmployees findUnderContracts();

    void registerName(EmployeeName employeeName);

    void registerMailAddress(EmployeeMailAddress employeeMailAddress);

    void registerPhoneNumber(EmployeePhoneNumber employeePhoneNumber);

    void registerInspireContract(EmployeeNumber employeeNumber);

    void registerExpireContract(EmployeeNumber employeeNumber);

    EmployeeNumber registerNew();
}
