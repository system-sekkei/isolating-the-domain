package example.application.repository;

import example.domain.model.timerecord.WorkRecord;
import example.domain.model.timerecord.WorkMonth;
import example.domain.model.worker.WorkerNumber;
import example.domain.model.timerecord.WorkRecords;

/**
 * 勤務実績リポジトリ
 */
public interface WorkRecordRepository {

    void registerWorkRecord(WorkRecord workRecord);

    WorkRecords findWorkRecords(WorkerNumber workerNumber, WorkMonth month);
}
