package example.application.service.payroll;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.contract.ContractQueryService;
import example.domain.model.contract.Contracts;
import example.domain.model.payroll.ContractPayroll;
import example.domain.model.payroll.Payroll;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 給与参照サービス
 */
@Service
public class PayrollQueryService {
    ContractQueryService contractQueryService;
    AttendanceQueryService attendanceQueryService;

    public Payroll getPayroll(Worker worker, YearMonth yearMonth) {
        WorkerNumber workerNumber = worker.workerNumber();
        Contracts contracts = contractQueryService.getContracts(workerNumber, yearMonth.start(), yearMonth.end());
        // TODO streamを使わない
        // TODO 型にする（Listなどはserviceに登場しない
        List<ContractPayroll> contractPayrolls = contracts.value().stream().map(c -> new ContractPayroll(c, attendanceQueryService.getAttendances(workerNumber, c.startDate(), c.endDate()))).collect(Collectors.toList());
        return new Payroll(worker, yearMonth, contractPayrolls);
    }

    PayrollQueryService(ContractQueryService contractQueryService, AttendanceQueryService attendanceQueryService) {
        this.contractQueryService = contractQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }
}
