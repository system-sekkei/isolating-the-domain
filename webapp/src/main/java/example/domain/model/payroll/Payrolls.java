package example.domain.model.payroll;

import example.domain.model.attendance.WorkMonth;

import java.util.List;

/**
 * 給与一覧
 */
public class Payrolls {
    WorkMonth workMonth;
    List<Payroll> list;

    public Payrolls(WorkMonth workMonth, List<Payroll> list) {
        this.workMonth = workMonth;
        this.list = list;
    }

    public WorkMonth workMonth() {
        return workMonth;
    }

    public List<Payroll> list() {
        return list;
    }
}
