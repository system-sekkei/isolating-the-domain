package example.presentation.controller.employee;

import example.application.service.employee.EmployeeRecordService;
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

    @GetMapping
    String deleteThenRedirect(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber) {
        employeeRecordService.expireContract(employeeNumber);
        return "redirect:/employees";
    }

    EmployeeDeleteController(EmployeeRecordService employeeRecordService) {
        this.employeeRecordService = employeeRecordService;
    }
}
