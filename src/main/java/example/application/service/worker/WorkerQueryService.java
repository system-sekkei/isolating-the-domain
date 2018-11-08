package example.application.service.worker;

import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.model.worker.WorkerRepository;
import org.springframework.stereotype.Service;

/**
 * 従業員参照サービス
 */
@Service
public class WorkerQueryService {

    WorkerRepository workerRepository;

    /**
     * 従業員選択
     */
    public Worker choose(WorkerIdentifier workerIdentifier) {
        return workerRepository.choose(workerIdentifier);
    }

    /**
     * 契約中従業員一覧
     */
    public ContractingWorkers contractingWorkers() {
        return workerRepository.findUnderContracts();
    }

    WorkerQueryService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}
