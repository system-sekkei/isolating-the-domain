package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MonthlyHourlyWages;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

/**
 * 契約参照サービス
 */
@Service
public class ContractQueryService {
    ContractRepository contractRepository;

    /**
     * 時給取得
     */
    public HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay) {
        return contractRepository.getHourlyWage(workerNumber, workDay);
    }

    /**
     * 月内時給変遷
     */
    public MonthlyHourlyWages getMonthlyHourlyWage(WorkerNumber workerNumber, YearMonth yearMonth) {
        return contractRepository.getMonthlyHourlyWage(workerNumber, yearMonth);
    }

    /**
     *　雇用契約変遷
     */
    public Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate) {
        return contractRepository.getContracts(workerNumber, startDate, endDate);
    }

    ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
