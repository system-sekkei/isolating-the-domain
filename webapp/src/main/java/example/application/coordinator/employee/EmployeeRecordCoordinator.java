package example.application.coordinator.employee;

import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.Employee;
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
        employeeRecordService.registerName(employeeNumber, profile.name());
        employeeRecordService.registerMailAddress(employeeNumber, profile.mailAddress());
        employeeRecordService.registerPhoneNumber(employeeNumber, profile.phoneNumber());
        employeeRecordService.inspireContract(employeeNumber);
        return employeeNumber;
    }

    /**
     * 従業員個人情報更新
     */
    public void update(Employee employee) {
    	EmployeeNumber employeeNumber = employee.employeeNumber();
        employeeRecordService.registerName(employeeNumber, employee.name());
        employeeRecordService.registerMailAddress(employeeNumber, employee.mailAddress());
        employeeRecordService.registerPhoneNumber(employeeNumber, employee.phoneNumber());
    }
}
