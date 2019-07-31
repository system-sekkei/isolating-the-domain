package example.presentation.controller;

import example.application.service.employee.EmployeeQueryService;
import example.domain.model.employee.ContractingEmployees;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ダッシュボード
 */
@Controller
@RequestMapping("/")
class DashboardController {
    EmployeeQueryService employeeQueryService;

    @ModelAttribute("employees")
    ContractingEmployees employees() {
        return employeeQueryService.contractingEmployees();
    }

    @GetMapping
    String show() {
        return "dashboard";
    }

    DashboardController(EmployeeQueryService employeeQueryService) {
        this.employeeQueryService = employeeQueryService;
    }
}
