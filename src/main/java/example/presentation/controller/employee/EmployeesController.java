package example.presentation.controller.employee;

import example.application.service.contract.ContractQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.contract.Contracts;
import example.domain.model.employee.ContractingEmployees;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員の一覧
 */
@Controller
@RequestMapping("employees")
public class EmployeesController {

    EmployeeQueryService employeeQueryService;
    ContractQueryService contractQueryService;

    @GetMapping
    String employees(Model model) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        Contracts contracts = contractQueryService.findContracts(contractingEmployees);

        model.addAttribute("contracts", contracts);
        return "employee/list";
    }

    EmployeesController(EmployeeQueryService employeeQueryService, ContractQueryService contractQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.contractQueryService = contractQueryService;
    }
}
