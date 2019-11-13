package example.application.coordinator.payroll;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.contract.ContractQueryService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractWages;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.payroll.Payroll;
import example.domain.model.payroll.Payrolls;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 給与参照コーディネーター
 */
@Service
public class PayrollQueryCoordinator {
    ContractQueryService contractQueryService;
    AttendanceQueryService attendanceQueryService;

    /**
     * 月次給与取得
     */
    public Payrolls payrolls(ContractingEmployees contractingEmployees, WorkMonth workMonth) {
        List<Payroll> list = new ArrayList<>();
        for (Employee employee : contractingEmployees.list()) {
            list.add(payroll(employee, workMonth));
        }
        return new Payrolls(workMonth, list);
    }

    /**
     * 従業員別月次給与取得
     */
    public Payroll payroll(Employee employee, WorkMonth workMonth) {
        ContractWages contractWages = contractQueryService.getContractWages(employee);
        Attendance attendance = attendanceQueryService.findAttendance(employee, workMonth);

        return new Payroll(new Contract(employee, contractWages), attendance);
    }

    PayrollQueryCoordinator(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}
