package example.domain.model.payroll;

import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.PayableWork;
import example.domain.model.contract.Contract;
import example.domain.model.contract.wage.WageCondition;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;
import example.domain.model.wage.HourlyWage;

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
                    .add(new PaymentWorkTime(payableWork.nightWorkTime()).multiply(wageCondition.nightHourlyExtraWage().value()));
        }

        // TODO:
        paymentAmount = paymentAmount
                .add(new PaymentWorkTime(attendance.overLegalMoreThan60HoursWorkTime().quarterHour()).multiply(new HourlyWage(0)))
                .add(new PaymentWorkTime(attendance.overLegalWithin60HoursWorkTime().quarterHour()).multiply(new HourlyWage(0)))
                .add(new PaymentWorkTime(attendance.legalDaysOffWorkTime().quarterHour()).multiply(new HourlyWage(0)));

        return paymentAmount;
    }

    public PayrollStatus payrollStatus() {
        return PayrollStatus.from(contract.contractStatus(attendance.attendDates().toDates()));
    }
}
