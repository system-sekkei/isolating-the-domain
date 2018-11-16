package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Service;

/**
 * 契約登録サービス
 */
@Service
public class ContractRecordService {
    ContractRepository contractRepository;

    /**
     * 時給登録
     */
    public void registerHourlyWage (WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        contractRepository.registerHourlyWage(workerNumber, applyDate, hourlyWage);
    }

    ContractRecordService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
