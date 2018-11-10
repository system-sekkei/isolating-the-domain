package example.application.service.worker;

import example.application.repository.WorkerRepository;
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
    public WorkerNumber prepareNewContract() {
        return workerRepository.registerNew();
    }

    /**
     * 従業員名登録
     */
    public void registerName(WorkerNumber workerNumber, Name name) {
        workerRepository.registerName(workerNumber, name);
    }

    /**
     * 従業員メールアドレス登録
     */
    public void registerMailAddress(WorkerNumber workerNumber, MailAddress mailAddress) {
        workerRepository.registerMailAddress(workerNumber, mailAddress);
    }

    /**
     * 従業員電話番号登録
     */
    public void registerPhoneNumber(WorkerNumber workerNumber, PhoneNumber phoneNumber) {
        workerRepository.registerPhoneNumber(workerNumber, phoneNumber);
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
