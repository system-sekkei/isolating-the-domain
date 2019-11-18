package example.infrastructure.datasource.employee;

import example.application.repository.EmployeeRepository;
import example.domain.model.employee.*;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDataSource implements EmployeeRepository {
    EmployeeMapper mapper;

    @Override
    public Employee choose(EmployeeNumber employeeNumber) {
        return mapper.selectByEmployeeNumber(employeeNumber);
    }

    @Override
    public ContractingEmployees findUnderContracts() {
        return new ContractingEmployees(mapper.selectContracts());
    }

    @Override
    public EmployeeNumber registerNew() {
        EmployeeNumber employeeNumber = new EmployeeNumber(mapper.newEmployeeNumber());
        mapper.insertEmployee(employeeNumber);
        return employeeNumber;
    }

    @Override
    public void registerName(Employee employee) {
        Integer nameId = mapper.newEmployeeNameIdentifier();
        EmployeeNumber employeeNumber = employee.employeeNumber();
        Name name = employee.name();
        mapper.insertEmployeeNameHistory(nameId, employeeNumber, name);
        mapper.deleteEmployeeName(employeeNumber);
        mapper.insertEmployeeName(employeeNumber, nameId, name);
    }

    @Override
    public void registerMailAddress(Employee employee) {
        Integer mailAddressId = mapper.newEmployeeMailAddressIdentifier();
        EmployeeNumber employeeNumber = employee.employeeNumber();
        MailAddress mailAddress = employee.mailAddress();
        mapper.insertEmployeeMailAddressHistory(mailAddressId, employeeNumber, mailAddress);
        mapper.deleteEmployeeMailAddress(employeeNumber);
        mapper.insertEmployeeMailAddress(employeeNumber, mailAddressId, mailAddress);
    }

    @Override
    public void registerPhoneNumber(Employee employee) {
        Integer phoneNumberId = mapper.newEmployeePhoneNumberIdentifier();
        EmployeeNumber employeeNumber = employee.employeeNumber();
        PhoneNumber phoneNumber = employee.phoneNumber();
        mapper.insertEmployeePhoneNumberHistory(phoneNumberId, employeeNumber, phoneNumber);
        mapper.deleteEmployeePhoneNumber(employeeNumber);
        mapper.insertEmployeePhoneNumber(employeeNumber, phoneNumberId, phoneNumber);
    }

    @Override
    public void registerInspireContract(Employee employee) {
        mapper.insertInspireContract(employee.employeeNumber());
    }

    @Override
    public void registerExpireContract(Employee employee) {
        mapper.deleteInspireContract(employee.employeeNumber());
        mapper.insertExpireContract(employee.employeeNumber());
    }

    public EmployeeDataSource(EmployeeMapper mapper) {
        this.mapper = mapper;
    }
}
