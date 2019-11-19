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
    public void registerName(EmployeeName employeeName) {
        Integer nameId = mapper.newEmployeeNameIdentifier();
        EmployeeNumber employeeNumber = employeeName.employeeNumber();
        Name name = employeeName.name();
        mapper.insertEmployeeNameHistory(nameId, employeeNumber, name);
        mapper.deleteEmployeeName(employeeNumber);
        mapper.insertEmployeeName(employeeNumber, nameId, name);
    }

    @Override
    public void registerMailAddress(EmployeeMailAddress employeeMailAddress) {
        Integer mailAddressId = mapper.newEmployeeMailAddressIdentifier();
        EmployeeNumber employeeNumber = employeeMailAddress.employeeNumber();
        MailAddress mailAddress = employeeMailAddress.mailAddress();
        mapper.insertEmployeeMailAddressHistory(mailAddressId, employeeNumber, mailAddress);
        mapper.deleteEmployeeMailAddress(employeeNumber);
        mapper.insertEmployeeMailAddress(employeeNumber, mailAddressId, mailAddress);
    }

    @Override
    public void registerPhoneNumber(EmployeePhoneNumber employeePhoneNumber) {
        Integer phoneNumberId = mapper.newEmployeePhoneNumberIdentifier();
        EmployeeNumber employeeNumber = employeePhoneNumber.employeeNumber();
        PhoneNumber phoneNumber = employeePhoneNumber.phoneNumber();
        mapper.insertEmployeePhoneNumberHistory(phoneNumberId, employeeNumber, phoneNumber);
        mapper.deleteEmployeePhoneNumber(employeeNumber);
        mapper.insertEmployeePhoneNumber(employeeNumber, phoneNumberId, phoneNumber);
    }

    @Override
    public void registerInspireContract(EmployeeNumber employeeNumber) {
        mapper.insertInspireContract(employeeNumber);
    }

    @Override
    public void registerExpireContract(EmployeeNumber employeeNumber) {
        mapper.deleteInspireContract(employeeNumber);
        mapper.insertExpireContract(employeeNumber);
    }

    public EmployeeDataSource(EmployeeMapper mapper) {
        this.mapper = mapper;
    }
}
