package example.application.coordinator.employee;

import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Profile;
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
    public EmployeeNumber register(Profile profile) {
        EmployeeNumber employeeNumber = employeeRecordService.prepareNewContract();
        employeeRecordService.registerName(profile.updateName(employeeNumber));
        employeeRecordService.registerMailAddress(profile.updateMailAddress(employeeNumber));
        employeeRecordService.registerPhoneNumber(profile.updatePhoneNumber(employeeNumber));
        employeeRecordService.inspireContract(employeeNumber);
        return employeeNumber;
    }
}
