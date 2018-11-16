package example.application.service.payroll;

import example.application.repository.PayrollRepository;
import example.application.service.attendance.AttendanceQueryService;
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
    PayrollRepository payrollRepository;
    AttendanceQueryService attendanceQueryService;

    /**
     * 時給取得
     */
    public HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay) {
        return payrollRepository.getHourlyWage(workerNumber, workDay);
    }

    /**
     * 月内時給変遷
     */
    public MonthlyHourlyWages getMonthlyHourlyWage(WorkerNumber workerNumber, YearMonth yearMonth) {
        return payrollRepository.getMonthlyHourlyWage(workerNumber, yearMonth);
    }

    /**
     *
     */
    public Payroll getPayroll(Worker worker, YearMonth yearMonth) {
        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(worker.workerNumber(), yearMonth);
        MonthlyHourlyWages monthlyHourlyWage = getMonthlyHourlyWage(worker.workerNumber(), yearMonth);
        return new Payroll(worker, monthlyAttendances, monthlyHourlyWage);
    }

    PayrollQueryService(PayrollRepository payrollRepository, AttendanceQueryService attendanceQueryService) {
        this.payrollRepository = payrollRepository;
        this.attendanceQueryService = attendanceQueryService;
    }
}
