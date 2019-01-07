package example.application.repository;

import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.worker.WorkerNumber;

/**
 * 勤務実績リポジトリ
 */
public interface WorkRecordRepository {

    void registerWorkRecord(TimeRecord timeRecord);

    TimeRecords findWorkRecords(WorkerNumber workerNumber, WorkMonth month);
}
