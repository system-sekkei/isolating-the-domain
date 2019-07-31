package example.api.controller.payroll;

import example.api.view.payroll.PayrollListView;
import example.application.coordinator.payroll.PayrollQueryCoordinator;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.payroll.Payrolls;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 給与一覧の取得API
 */
@RestController
@RequestMapping("/api/payroll")
public class PayrollAPI {
    MessageSource messageSource;
    EmployeeQueryService employeeQueryService;
    PayrollQueryCoordinator payrollQueryCoordinator;

    public PayrollAPI(MessageSource messageSource, EmployeeQueryService employeeQueryService, PayrollQueryCoordinator payrollQueryCoordinator) {
        this.messageSource = messageSource;
        this.employeeQueryService = employeeQueryService;
        this.payrollQueryCoordinator = payrollQueryCoordinator;
    }

    @GetMapping
    PayrollListView get() {
        return get(new WorkMonth());
    }

    @GetMapping("{workMonth}")
    PayrollListView get(@PathVariable("workMonth") WorkMonth workMonth) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        Payrolls payrolls = payrollQueryCoordinator.payrolls(contractingEmployees, workMonth);
        return new PayrollListView(payrolls, messageSource);
    }
}
