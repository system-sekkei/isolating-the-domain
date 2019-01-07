package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.WorkerContracts;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Service;

/**
 * 契約参照サービス
 */
@Service
public class ContractQueryService {
    ContractRepository contractRepository;

    /**
     * 契約取得
     */
    public Contracts getContracts(WorkerNumber workerNumber) {
        return contractRepository.getContracts(workerNumber);
    }

    /**
     * 従業員契約一覧
     */
    public WorkerContracts findWorkerContracts(ContractingWorkers contractingWorkers) {
        return contractRepository.findWorkerContracts(contractingWorkers);
    }

    ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
