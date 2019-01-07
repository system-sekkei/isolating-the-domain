package example.infrastructure.datasource.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkRecordDatasource implements WorkRecordRepository {
    WorkRecordMapper mapper;

    @Override
    public void registerWorkRecord(TimeRecord timeRecord) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, timeRecord.workerNumber(), timeRecord);
        mapper.deleteWorkTime(timeRecord.workerNumber(), timeRecord.workDate());
        mapper.insertWorkTime(timeRecord.workerNumber(), identifier, timeRecord);
    }

    @Override
    public TimeRecords findWorkRecords(WorkerNumber workerNumber, WorkMonth month) {
        List<TimeRecord> timeRecords = mapper.selectByMonth(workerNumber, month.yyyyMM());
        return new TimeRecords(timeRecords);
    }

    WorkRecordDatasource(WorkRecordMapper mapper) {
        this.mapper = mapper;
    }
}
