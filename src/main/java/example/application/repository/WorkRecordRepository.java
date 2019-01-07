package example.application.repository;

import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkMonth;
import example.domain.model.timerecord.WorkRecords;
import example.domain.model.worker.WorkerNumber;

/**
 * 勤務実績リポジトリ
 */
public interface WorkRecordRepository {

    void registerWorkRecord(TimeRecord timeRecord);

    WorkRecords findWorkRecords(WorkerNumber workerNumber, WorkMonth month);
}
