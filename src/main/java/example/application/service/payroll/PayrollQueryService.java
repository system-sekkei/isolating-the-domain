package example.application.service.payroll;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.contract.ContractQueryService;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.WorkerContract;
import example.domain.model.payroll.Payroll;
import example.domain.model.payroll.Payrolls;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 給与参照サービス
 */
@Service
public class PayrollQueryService {
    ContractQueryService contractQueryService;
    AttendanceQueryService attendanceQueryService;

    /**
     * 月次給与取得
     */
    public Payrolls payrolls(ContractingWorkers contractingWorkers, WorkMonth workMonth) {
        List<Payroll> list = new ArrayList<>();
        for (Worker worker : contractingWorkers.list()) {
            list.add(payroll(worker, workMonth));
        }
        return new Payrolls(workMonth, list);
    }

    /**
     * 従業員別月次給与取得
     */
    public Payroll payroll(Worker worker, WorkMonth workMonth) {
        WorkerNumber workerNumber = worker.workerNumber();
        Contracts contracts = contractQueryService.getContracts(workerNumber);
        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(workerNumber, workMonth);

        return new Payroll(new WorkerContract(worker, contracts), monthlyAttendances);
    }

    PayrollQueryService(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}
