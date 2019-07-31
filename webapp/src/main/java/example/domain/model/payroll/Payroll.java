package example.domain.model.payroll;

import example.domain.model.attendance.Attendance;
import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractWage;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;

import java.math.BigDecimal;

/**
 * 給与
 */
public class Payroll {

    Contract contract;
    Attendance attendance;

    public Payroll(Contract contract, Attendance attendance) {
        this.contract = contract;
        this.attendance = attendance;
    }

    public EmployeeNumber employeeNumber() {
        return contract.employeeNumber();
    }

    public Name employeeName() {
        return contract.employeeName();
    }

    public PaymentAmount totalPayment() {
        PaymentAmount paymentAmount = new PaymentAmount(BigDecimal.ZERO);

        for (TimeRecord timeRecord : attendance.timeRecords().list()) {
            ContractWage contractWage = contract.availableContractAt(timeRecord.date());

            PaymentAmount oneDayAmount = new PaymentAmount(timeRecord.actualWorkTime(), contractWage.wageCondition());
            paymentAmount = paymentAmount.add(oneDayAmount);
        }
        return paymentAmount;
    }

    public PayrollStatus payrollStatus() {
        return PayrollStatus.from(contract.contractStatus(attendance.attendDates().toDates()));
    }
}
