package example.presentation.controller.payroll;

import example.application.service.payroll.PayrollQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.payroll.Payrolls;
import example.domain.model.employee.ContractingEmployees;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 給与の一覧
 */
@Controller
@RequestMapping("payroll")
public class PayrollController {

    EmployeeQueryService employeeQueryService;
    PayrollQueryService payrollQueryService;

    public PayrollController(EmployeeQueryService employeeQueryService, PayrollQueryService payrollQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.payrollQueryService = payrollQueryService;
    }

    @GetMapping
    String employees(Model model) {
        return employees(model, new WorkMonth());
    }

    @GetMapping("{workMonth}")
    String employees(Model model, @PathVariable("workMonth") WorkMonth workMonth) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        Payrolls payrolls = payrollQueryService.payrolls(contractingEmployees, workMonth);
        model.addAttribute("payrolls", payrolls);
        return "payroll/list";
    }
}
