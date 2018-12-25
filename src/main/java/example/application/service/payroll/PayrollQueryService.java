package example.application.service.payroll;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.contract.ContractQueryService;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.contract.Contracts;
import example.domain.model.payroll.Payroll;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

/**
 * 給与参照サービス
 */
@Service
public class PayrollQueryService {
    ContractQueryService contractQueryService;
    AttendanceQueryService attendanceQueryService;

    public Payroll getPayroll(Worker worker, YearMonth yearMonth) {
        WorkerNumber workerNumber = worker.workerNumber();
        Contracts contracts = contractQueryService.getContracts(workerNumber);
        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(workerNumber, new WorkMonth(yearMonth));

        return new Payroll(worker, contracts, monthlyAttendances);
    }

    PayrollQueryService(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}
