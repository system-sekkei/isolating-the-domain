package example.application.service.worker;

import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.model.worker.WorkerRepository;
import example.domain.model.worker.Workers;
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
    public Worker findById(WorkerIdentifier id) {
        return workerRepository.findBy(id);
    }

    /**
     * 従業員一覧
     */
    public Workers list() {
        return workerRepository.list();
    }

    WorkerQueryService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}
