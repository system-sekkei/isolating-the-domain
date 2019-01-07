package example.application.service.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.timerecord.TimeRecord;
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
    public void registerWorkRecord(TimeRecord timeRecord) {
        workRecordRepository.registerWorkRecord(timeRecord);
    }

    WorkRecordRecordService(WorkRecordRepository workRecordRepository) {
        this.workRecordRepository = workRecordRepository;
    }
}
