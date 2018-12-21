package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.HourlyWageContract;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Service;

/**
 * 契約登録サービス
 */
@Service
public class ContractRecordService {
    ContractRepository contractRepository;

    ContractRecordService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * 時給登録
     */
    public void registerHourlyWage(WorkerNumber workerNumber, Date startDate, HourlyWageContract hourlyWageContract) {
        contractRepository.registerHourlyWage(workerNumber, startDate, hourlyWageContract);
    }

    public void stopHourlyWageContract(WorkerNumber workerNumber, Date lastDate) {
        contractRepository.stopHourlyWageContract(workerNumber, lastDate);
    }
}
