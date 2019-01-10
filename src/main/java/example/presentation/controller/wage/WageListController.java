package example.presentation.controller.wage;

import example.application.service.contract.ContractQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.contract.ContractWages;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員ごとの時給の変遷
 */
@Controller
@RequestMapping("wages/{employeeNumber}")
public class WageListController {

    EmployeeQueryService employeeQueryService;
    ContractQueryService contractQueryService;

    public WageListController(EmployeeQueryService employeeQueryService, ContractQueryService contractQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.contractQueryService = contractQueryService;
    }

    @ModelAttribute("employee")
    Employee employee(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber) {
        return employeeQueryService.choose(employeeNumber);
    }

    @GetMapping
    public String list(Employee employee, Model model) {
        ContractWages contractWages = contractQueryService.getContractWages(employee.employeeNumber());
        model.addAttribute("contractWages", contractWages);
        return "wage/list";
    }

}
