package example.application.repository;

import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkMonth;
import example.domain.model.worker.WorkerNumber;
import example.domain.model.workrecord.WorkRecords;

/**
 * 勤務実績リポジトリ
 */
public interface WorkRecordRepository {

    void registerWorkRecord(WorkRecord workRecord);

    WorkRecords findWorkRecords(WorkerNumber workerNumber, WorkMonth month);
}
