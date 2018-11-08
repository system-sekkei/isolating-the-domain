package example.domain.model.worker;

/**
 * 従業員リポジトリ
 */
public interface WorkerRepository {
    Worker findBy(WorkerIdentifier id);

    Workers list();

    Worker register(UserCandidate user);

    void updateName(WorkerIdentifier identifier, Name name);

    void updateMailAddress(WorkerIdentifier identifier, MailAddress mailAddress);

    void updatePhoneNumber(WorkerIdentifier identifier, PhoneNumber phoneNumber);

    void delete(Worker worker);
}
