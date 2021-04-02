package example.application.coordinator.employee;

import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.*;
import org.springframework.stereotype.Service;

/**
 * 従業員登録コーディネーター
 */
@Service
public class EmployeeRecordCoordinator {

    EmployeeRecordService employeeRecordService;

    public EmployeeRecordCoordinator(EmployeeRecordService employeeRecordService) {
        this.employeeRecordService = employeeRecordService;
    }

    /**
     * 従業員登録
     */
    public EmployeeNumber register(EmployeeToRegister employeeToRegister) {
        EmployeeNumber employeeNumber = employeeRecordService.prepareNewContract();
        employeeRecordService.registerName(new NameToChange(employeeNumber, employeeToRegister.name()));
        employeeRecordService.registerMailAddress(new MailAddressToChange(employeeNumber, employeeToRegister.mailAddress()));
        employeeRecordService.registerPhoneNumber(new PhoneNumberToChange(employeeNumber, employeeToRegister.phoneNumber()));
        employeeRecordService.inspireContract(employeeNumber);
        return employeeNumber;
    }
}
