package example.application.service.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.timerecord.WorkRecord;
import org.springframework.stereotype.Service;

/**
 * 勤務実績記録サービス
 */
@Service
public class WorkRecordRecordService {

    WorkRecordRepository workRecordRepository;

    /**
     * 勤務登録
     */
    public void registerWorkRecord(WorkRecord workRecord) {
        workRecordRepository.registerWorkRecord(workRecord);
    }

    WorkRecordRecordService(WorkRecordRepository workRecordRepository) {
        this.workRecordRepository = workRecordRepository;
    }
}
