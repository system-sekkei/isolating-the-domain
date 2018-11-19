package example.application.service.payroll;

import java.util.List;
import java.util.stream.Collectors;

import example.domain.model.contract.Contracts;
import example.domain.model.payroll.ContractPayroll;
import example.domain.model.payroll.Payroll2;
import example.domain.model.worker.WorkerNumber;
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

    public Payroll2 getPayroll2(Worker worker, YearMonth yearMonth) {
        WorkerNumber workerNumber = worker.workerNumber();
        Contracts contracts = contractQueryService.getContracts(workerNumber, yearMonth.start(), yearMonth.end());
        List<ContractPayroll> contractPayrolls = contracts.value().stream().map(c -> new ContractPayroll(c, attendanceQueryService.getAttendances(workerNumber, c.startDate(), c.endDate().get()))).collect(Collectors.toList());
        return new Payroll2(worker, yearMonth, contractPayrolls);
    }

    PayrollQueryService(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}
