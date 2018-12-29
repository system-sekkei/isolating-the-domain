package example.infrastructure.datasource.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkRecords;
import example.domain.model.workrecord.WorkMonth;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkRecordDatasource implements WorkRecordRepository {
    WorkRecordMapper mapper;

    @Override
    public void registerWorkRecord(WorkRecord workRecord) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, workRecord.workerNumber(), workRecord);
        mapper.deleteWorkTime(workRecord.workerNumber(), workRecord.workDate());
        mapper.insertWorkTime(workRecord.workerNumber(), identifier, workRecord);
    }

    @Override
    public WorkRecords findWorkRecords(WorkerNumber workerNumber, WorkMonth month) {
        List<WorkRecord> workRecords = mapper.selectByMonth(workerNumber, month.yyyyMM());
        return new WorkRecords(workRecords);
    }

    WorkRecordDatasource(WorkRecordMapper mapper) {
        this.mapper = mapper;
    }
}
