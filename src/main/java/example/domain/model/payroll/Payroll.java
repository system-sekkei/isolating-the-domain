package example.domain.model.payroll;

import example.domain.model.worker.Worker;
import example.domain.type.date.YearMonth;

import java.math.BigDecimal;
import java.util.List;

/**
 * 給与
 */
public class Payroll {
    Worker worker;
    YearMonth yearMonth;
    List<DairyPayroll> payrolls;

    public Payroll(Worker worker, YearMonth yearMonth, List<DairyPayroll> payrolls) {
        this.worker = worker;
        this.yearMonth = yearMonth;
        this.payrolls = payrolls;
    }

    Wage wage() {
        Wage wage = new Wage(BigDecimal.ZERO);
        for(DairyPayroll p : payrolls) {
            wage = wage.add(p.wage());
        }
        return wage;
    }
}
