package example.application.service.worker;

import example.domain.model.worker.*;
import org.springframework.stereotype.Service;

/**
 * 従業員登録更新サービス
 */
@Service
public class WorkerRecordService {

    WorkerRepository workerRepository;

    /**
     * 従業員契約準備
     */
    public WorkerIdentifier prepareNewContract() {
        return workerRepository.registerNew();
    }

    /**
     * 従業員名登録
     */
    public void updateName(WorkerIdentifier identifier, Name name) {
        workerRepository.updateName(identifier, name);
    }

    /**
     * 従業員メールアドレス登録
     */
    public void updateMailAddress(WorkerIdentifier identifier, MailAddress mailAddress) {
        workerRepository.updateMailAddress(identifier, mailAddress);
    }

    /**
     * 従業員電話番号登録
     */
    public void updatePhoneNumber(WorkerIdentifier identifier, PhoneNumber phoneNumber) {
        workerRepository.updatePhoneNumber(identifier, phoneNumber);
    }

    /**
     * 従業員契約終了
     */
    public void expirationContract(Worker worker) {
        workerRepository.registerExpirationContract(worker);
    }

    WorkerRecordService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}
