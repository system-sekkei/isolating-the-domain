package example.domain.model.payroll;

import example.domain.model.attendance.Attendance;
import example.domain.model.contract.Contract;
import example.domain.model.contract.EmploymentContract;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;

import java.math.BigDecimal;

/**
 * 給与
 */
public class Payroll {

    EmploymentContract employmentContract;
    Attendance attendance;

    public Payroll(EmploymentContract employmentContract, Attendance attendance) {
        this.employmentContract = employmentContract;
        this.attendance = attendance;
    }

    public EmployeeNumber employeeNumber() {
        return employmentContract.employeeNumber();
    }

    public Name employeeName() {
        return employmentContract.employeeName();
    }

    public PaymentAmount totalPaymentAmount() {
        PaymentAmount paymentAmount = new PaymentAmount(BigDecimal.ZERO);

        for (TimeRecord timeRecord : attendance.listAvailableWorkRecord()) {
            Contract contract = employmentContract.availableContractAt(timeRecord.workDate().value());

            PaymentAmount oneDayAmount = new PaymentAmount(timeRecord.actualWorkTime(), contract.wageCondition());
            paymentAmount = paymentAmount.add(oneDayAmount);
        }
        return paymentAmount;
    }

    public PayrollStatus payrollStatus() {
        if (attendance.notWorking()) {
            return PayrollStatus.稼働登録無し;
        }
        return PayrollStatus.from(employmentContract.contractStatus(attendance.firstWorkDate().value()));
    }
}
