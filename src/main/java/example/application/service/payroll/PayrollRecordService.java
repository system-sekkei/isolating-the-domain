package example.application.service.payroll;

import example.application.repository.PayrollRepository;
import example.application.repository.WorkerRepository;
import example.domain.model.payroll.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Service;

/**
 * 給与登録サービス
 */
@Service
public class PayrollRecordService {
    PayrollRepository payrollRepository;

    /**
     * 時給登録
     */
    public void registerHourlyWage (WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        payrollRepository.registerHourlyWage(workerNumber, applyDate, hourlyWage);
    }

    PayrollRecordService(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }
}
