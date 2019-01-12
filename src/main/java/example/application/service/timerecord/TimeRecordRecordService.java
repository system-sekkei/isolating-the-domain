package example.application.service.timerecord;

import example.application.repository.TimeRecordRepository;
import example.domain.model.timerecord.TimeRecord;
import org.springframework.stereotype.Service;

/**
 * 勤務実績記録サービス
 */
@Service
public class TimeRecordRecordService {

    TimeRecordRepository timeRecordRepository;

    /**
     * 勤務登録
     */
    public void registerTimeRecord(TimeRecord timeRecord) {
        timeRecordRepository.registerTimeRecord(timeRecord);
    }

    TimeRecordRecordService(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }
}
