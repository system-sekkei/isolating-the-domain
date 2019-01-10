package example.application.repository;

import example.domain.model.employee.*;

/**
 * 従業員リポジトリ
 */
public interface EmployeeRepository {
    Employee choose(EmployeeNumber employeeNumber);

    ContractingEmployees findUnderContracts();

    void registerName(EmployeeNumber employeeNumber, Name name);

    void registerMailAddress(EmployeeNumber employeeNumber, MailAddress mailAddress);

    void registerPhoneNumber(EmployeeNumber employeeNumber, PhoneNumber phoneNumber);

    void registerInspireContract(EmployeeNumber employeeNumber);

    void registerExpireContract(Employee employee);

    EmployeeNumber registerNew();
}
