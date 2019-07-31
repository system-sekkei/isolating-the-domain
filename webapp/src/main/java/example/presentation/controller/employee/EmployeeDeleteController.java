package example.presentation.controller.employee;

import example.application.service.employee.EmployeeQueryService;
import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員の削除
 */
@Controller
@RequestMapping("employees/{employeeNumber}/delete")
public class EmployeeDeleteController {

    EmployeeRecordService employeeRecordService;
    EmployeeQueryService employeeQueryService;

    @GetMapping(value = "")
    String deleteThenRedirect(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber) {
        Employee employee = employeeQueryService.choose(employeeNumber);
        employeeRecordService.expireContract(employee);
        return "redirect:/employees";
    }

    EmployeeDeleteController(EmployeeRecordService employeeRecordService, EmployeeQueryService employeeQueryService) {
        this.employeeRecordService = employeeRecordService;
        this.employeeQueryService = employeeQueryService;
    }
}
