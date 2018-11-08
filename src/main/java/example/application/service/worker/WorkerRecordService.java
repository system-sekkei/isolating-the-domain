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
     * 従業員契約
     */
    public Worker register(UserCandidate user) {
        return workerRepository.register(user);
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
    public void delete(Worker worker) {
        workerRepository.delete(worker);
    }

    WorkerRecordService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}
