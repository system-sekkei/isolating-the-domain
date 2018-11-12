package example.application.service.payroll;

import example.application.repository.PayrollRepository;
import example.domain.model.payroll.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Service;

/**
 * 給与参照サービス
 */
@Service
public class PayrollQueryService {
    PayrollRepository payrollRepository;

    /**
     * 時給取得
     */
    public HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay) {
        return payrollRepository.getHourlyWage(workerNumber, workDay);
    }

    PayrollQueryService(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }
}
