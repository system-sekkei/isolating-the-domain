package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Service;

/**
 * 契約参照サービス
 */
@Service
public class ContractQueryService {
    ContractRepository contractRepository;

    public Contracts getContracts(WorkerNumber workerNumber) {
        return contractRepository.getContracts(workerNumber);
    }

    /**
     *　雇用契約変遷
     */
    public ContractHistory getContractHistory(WorkerNumber workerNumber) {
        return new ContractHistory(getContracts(workerNumber));
    }

    ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

}
