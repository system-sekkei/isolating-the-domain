package example.application.service.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.contract.ContractQueryService;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.contract.MonthlyHourlyWages;
import example.domain.model.payroll.DairyPayroll;
import example.domain.model.payroll.Payroll;
import example.domain.model.worker.Worker;
import example.domain.type.date.YearMonth;

/**
 * 給与参照サービス
 */
@Service
public class PayrollQueryService {
    ContractQueryService contractQueryService;
    AttendanceQueryService attendanceQueryService;

    /**
     *
     */
    public Payroll getPayroll(Worker worker, YearMonth yearMonth) {
        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(worker.workerNumber(), yearMonth);
        MonthlyHourlyWages monthlyHourlyWage = contractQueryService.getMonthlyHourlyWage(worker.workerNumber(), yearMonth);
        List<DairyPayroll> payrolls = yearMonth.days().stream().map(
                day -> new DairyPayroll(day, monthlyAttendances.get(day), monthlyHourlyWage.get(day))).collect(Collectors.toList());
        return new Payroll(worker, yearMonth, payrolls);
    }

    PayrollQueryService(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}
