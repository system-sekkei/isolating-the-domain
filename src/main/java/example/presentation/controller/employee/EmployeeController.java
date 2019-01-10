package example.presentation.controller.employee;

import example.application.service.employee.EmployeeQueryService;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 従業員の詳細
 */
@Controller
@RequestMapping("employees/{employeeNumber}")
public class EmployeeController {

    EmployeeQueryService employeeQueryService;

    public EmployeeController(EmployeeQueryService employeeQueryService) {
        this.employeeQueryService = employeeQueryService;
    }

    @ModelAttribute("employee")
    Employee employee(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber) {
        return employeeQueryService.choose(employeeNumber);
    }

    @GetMapping
    public String init(Model model,
                       @RequestParam(value = "updateResult", required = false) String updateResult) {

        model.addAttribute("updateResult", updateResult);

        return "employee/detail";
    }
}
