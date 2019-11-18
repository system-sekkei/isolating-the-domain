package example.application.repository;

import example.domain.model.employee.*;

/**
 * 従業員リポジトリ
 */
public interface EmployeeRepository {
    Employee choose(EmployeeNumber employeeNumber);

    ContractingEmployees findUnderContracts();

    void registerName(Employee employee);

    void registerMailAddress(Employee employee);

    void registerPhoneNumber(Employee employee);

    void registerInspireContract(Employee employee);

    void registerExpireContract(Employee employee);

    EmployeeNumber registerNew();
}
