package example.application.service.payroll;

import example.application.repository.ContractRepository;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.contract.ContractQueryService;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MonthlyHourlyWages;
import example.domain.model.payroll.Payroll;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

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
        return new Payroll(worker, monthlyAttendances, monthlyHourlyWage);
    }

    PayrollQueryService(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}