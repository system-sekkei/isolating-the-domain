package example.domain.model.worker;

/**
 * 従業員リポジトリ
 */
public interface WorkerRepository {
    Worker choose(WorkerIdentifier workerIdentifier);

    ContractingWorkers findUnderContracts();

    void registerName(WorkerIdentifier workerIdentifier, Name name);

    void registerMailAddress(WorkerIdentifier workerIdentifier, MailAddress mailAddress);

    void registerPhoneNumber(WorkerIdentifier workerIdentifier, PhoneNumber phoneNumber);

    void registerExpireContract(Worker worker);

    WorkerIdentifier registerNew();
}
