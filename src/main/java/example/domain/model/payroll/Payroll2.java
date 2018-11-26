package example.domain.model.payroll;

import example.domain.model.worker.Worker;
import example.domain.type.date.YearMonth;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 給与
 */
public class Payroll2 {
    Worker worker;
    YearMonth yearMonth;
    List<ContractPayroll> payrolls;

    public Payroll2(Worker worker, YearMonth yearMonth, List<ContractPayroll> payrolls) {
        this.worker = worker;
        this.yearMonth = yearMonth;
        this.payrolls = payrolls;
    }

    public Wage wage() {
        Wage wage = new Wage(BigDecimal.ZERO);
        for(ContractPayroll p : payrolls) {
            wage = wage.add(p.wage());
        }
        return wage;
    }
}
