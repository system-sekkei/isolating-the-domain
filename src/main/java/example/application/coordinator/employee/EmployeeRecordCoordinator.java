package example.application.coordinator.employee;

import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;
import example.presentation.controller.employee.NewEmployee;
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
    public EmployeeNumber register(NewEmployee newEmployee, Name name) {
        EmployeeNumber employeeNumber = employeeRecordService.prepareNewContract();
        employeeRecordService.registerName(employeeNumber, name);
        employeeRecordService.registerMailAddress(employeeNumber, newEmployee.mailAddress());
        employeeRecordService.registerPhoneNumber(employeeNumber, newEmployee.phoneNumber());
        employeeRecordService.inspireContract(employeeNumber);
        return employeeNumber;
    }

    /**
     * 従業員個人情報更新
     */
    public void update(Employee employee) {
        employeeRecordService.registerName(employee.employeeNumber(), employee.name());
        employeeRecordService.registerMailAddress(employee.employeeNumber(), employee.mailAddress());
        employeeRecordService.registerPhoneNumber(employee.employeeNumber(), employee.phoneNumber());
    }
}
