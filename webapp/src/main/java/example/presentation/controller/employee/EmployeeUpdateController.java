package example.presentation.controller.employee;

import example.application.service.employee.EmployeeQueryService;
import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 従業員の更新
 */
@Controller
@RequestMapping("employees/{employeeNumber}/update")
class EmployeeUpdateController {

    EmployeeQueryService employeeQueryService;
    EmployeeRecordService employeeRecordService;

    @GetMapping
    String open(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber,
                Model model) {
        Employee employee = employeeQueryService.choose(employeeNumber);
        model.addAttribute("profile", employee.profile());
        return "employee/update/form";
    }

    @PostMapping("register")
    String registerThenRedirect(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber,
                                @Validated @ModelAttribute("profile") Profile profile, BindingResult result,
                                RedirectAttributes attributes) {
        if (result.hasErrors()) return "employee/update/form";

        Employee employee = new Employee(employeeNumber, profile);
        employeeRecordService.registerName(employee);
        employeeRecordService.registerMailAddress(employee);
        employeeRecordService.registerPhoneNumber(employee);

        attributes.addAttribute("updateResult", "completed");

        return "redirect:/employees/" + employeeNumber;
    }

    public EmployeeUpdateController(EmployeeQueryService employeeQueryService, EmployeeRecordService employeeRecordService) {
        this.employeeQueryService = employeeQueryService;
        this.employeeRecordService = employeeRecordService;
    }
}
