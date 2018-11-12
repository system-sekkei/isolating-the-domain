package example.application.repository;

import example.domain.model.worker.*;

/**
 * 従業員リポジトリ
 */
public interface WorkerRepository {
    Worker choose(WorkerNumber workerNumber);

    ContractingWorkers findUnderContracts();

    void registerName(WorkerNumber workerNumber, Name name);

    void registerMailAddress(WorkerNumber workerNumber, MailAddress mailAddress);

    void registerPhoneNumber(WorkerNumber workerNumber, PhoneNumber phoneNumber);

    void registerInspireContract(WorkerNumber worker);

    void registerExpireContract(Worker worker);

    WorkerNumber registerNew();
}
