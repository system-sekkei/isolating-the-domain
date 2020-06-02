package example.domain.model.payroll;

import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.PayableWork;
import example.domain.model.contract.Contract;
import example.domain.model.contract.wage.WageCondition;
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

        for (PayableWork payableWork : attendance.listPayableWork()) {
            WageCondition wageCondition = contract.wageConditionAt(payableWork.date());

            paymentAmount = paymentAmount.add(new PaymentWorkTime(payableWork.workTime()).multiply(wageCondition.baseHourlyWage().value()))
                    .add(new PaymentWorkTime(payableWork.nightWorkTime()).multiply(wageCondition.nightHourlyExtraWage().value()))
                    .add(new PaymentWorkTime(payableWork.overLegalMoreThan60HoursWorkTime(attendance).quarterHour()).multiply(wageCondition.overLegalMoreThan60HoursHourlyExtraWage().value()))
                    .add(new PaymentWorkTime(payableWork.overLegalWithin60HoursWorkTime(attendance).quarterHour()).multiply(wageCondition.overLegalWithin60HoursHourlyExtraWage().value())
                    .add(new PaymentWorkTime(payableWork.legalDaysOffWorkTime(attendance).quarterHour()).multiply(wageCondition.legalDaysOffHourlyExtraWage().value())));
        }

        return paymentAmount;
    }

    public PayrollStatus payrollStatus() {
        return PayrollStatus.from(contract.contractStatus(attendance.attendDates().toDates()));
    }
}
