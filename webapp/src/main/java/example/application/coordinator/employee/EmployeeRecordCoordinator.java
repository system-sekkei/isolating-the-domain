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
        Employee employee = new Employee(employeeNumber, profile);
        employeeRecordService.registerName(employee);
        employeeRecordService.registerMailAddress(employee);
        employeeRecordService.registerPhoneNumber(employee);
        employeeRecordService.inspireContract(employee);
        return employeeNumber;
    }
}
