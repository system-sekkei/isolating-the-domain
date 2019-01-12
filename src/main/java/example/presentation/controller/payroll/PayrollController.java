package example.presentation.controller.payroll;

import example.application.coordinator.payroll.PayrollQueryCoordinator;
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
    PayrollQueryCoordinator payrollQueryCoordinator;

    public PayrollController(EmployeeQueryService employeeQueryService, PayrollQueryCoordinator payrollQueryCoordinator) {
        this.employeeQueryService = employeeQueryService;
        this.payrollQueryCoordinator = payrollQueryCoordinator;
    }

    @GetMapping
    String employees(Model model) {
        return employees(model, new WorkMonth());
    }

    @GetMapping("{workMonth}")
    String employees(Model model, @PathVariable("workMonth") WorkMonth workMonth) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        Payrolls payrolls = payrollQueryCoordinator.payrolls(contractingEmployees, workMonth);
        model.addAttribute("payrolls", payrolls);
        return "payroll/list";
    }
}
