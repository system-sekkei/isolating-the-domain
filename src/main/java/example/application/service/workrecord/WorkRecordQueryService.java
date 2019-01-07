package example.application.service.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Service;

/**
 * 勤務実績参照サービス
 */
@Service
public class WorkRecordQueryService {

    WorkRecordRepository workRecordRepository;

    /**
     * 日次勤務実績取得
     */
    public TimeRecord workRecord(WorkerNumber workerNumber, WorkDate workDate) {
        return workRecordRepository.findWorkRecords(workerNumber, workDate.month()).at(workDate);
    }

    WorkRecordQueryService(WorkRecordRepository workRecordRepository) {
        this.workRecordRepository = workRecordRepository;
    }
}
