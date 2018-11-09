package example.domain.model.worker;

/**
 * 従業員リポジトリ
 */
public interface WorkerRepository {
    Worker choose(WorkerNumber workerNumber);

    ContractingWorkers findUnderContracts();

    void registerName(WorkerNumber workerNumber, Name name);

    void registerMailAddress(WorkerNumber workerNumber, MailAddress mailAddress);

    void registerPhoneNumber(WorkerNumber workerNumber, PhoneNumber phoneNumber);

    void registerExpireContract(Worker worker);

    WorkerNumber registerNew();
}
