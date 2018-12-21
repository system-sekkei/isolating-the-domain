package example.domain.model.payroll;

import example.domain.model.worker.Worker;
import example.domain.type.date.YearMonth;

/**
 * 給与
 */
public class Payroll {
    Worker worker;
    YearMonth yearMonth;
    ContractPayrolls contractPayrolls;

    public Payroll(Worker worker, YearMonth yearMonth, ContractPayrolls contractPayrolls) {
        this.worker = worker;
        this.yearMonth = yearMonth;
        this.contractPayrolls = contractPayrolls;
    }

    public Wage wage() {
        return contractPayrolls.wage();
    }

    // TODO 時給登録無しの場合に備考に出力する何かしら
}
