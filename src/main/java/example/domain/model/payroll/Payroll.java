package example.domain.model.payroll;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkTimeSummary;
import example.domain.model.contract.Contract;
import example.domain.model.contract.WorkerContract;
import example.domain.model.worker.Name;
import example.domain.model.worker.WorkerNumber;

import java.math.BigDecimal;

/**
 * 給与
 */
public class Payroll {

    WorkerContract workerContract;
    MonthlyAttendances monthlyAttendances;

    public Payroll(WorkerContract workerContract, MonthlyAttendances monthlyAttendances) {
        this.workerContract = workerContract;
        this.monthlyAttendances = monthlyAttendances;
    }

    public WorkerNumber workerNumber() {
        return workerContract.workerNumber();
    }

    public Name workerName() {
        return workerContract.workerName();
    }

    public PaymentAmount totalPaymentAmount() {
        PaymentAmount paymentAmount = new PaymentAmount(BigDecimal.ZERO);
        for (Contract contract : workerContract.listContracts()) {
            WorkTimeSummary workTimeSummary = monthlyAttendances.workTimeWithin(contract.period());
            paymentAmount = paymentAmount.add(PaymentAmount.from(workTimeSummary, contract.wageCondition()));
        }
        return paymentAmount;
    }

    public PayrollStatus payrollStatus() {
        if (monthlyAttendances.notWorking()) {
            return PayrollStatus.稼働登録無し;
        }
        if (workerContract.notContractedAt(monthlyAttendances.firstWorkDate().value())) {
            return PayrollStatus.時給登録無し;
        }

        return PayrollStatus.有効;
    }
}
