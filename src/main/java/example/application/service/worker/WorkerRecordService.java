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
    public void registerName(WorkerIdentifier workerIdentifier, Name name) {
        workerRepository.registerName(workerIdentifier, name);
    }

    /**
     * 従業員メールアドレス登録
     */
    public void registerMailAddress(WorkerIdentifier workerIdentifier, MailAddress mailAddress) {
        workerRepository.registerMailAddress(workerIdentifier, mailAddress);
    }

    /**
     * 従業員電話番号登録
     */
    public void registerPhoneNumber(WorkerIdentifier workerIdentifier, PhoneNumber phoneNumber) {
        workerRepository.registerPhoneNumber(workerIdentifier, phoneNumber);
    }

    /**
     * 従業員契約終了
     */
    public void expireContract(Worker worker) {
        workerRepository.registerExpireContract(worker);
    }

    WorkerRecordService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}
