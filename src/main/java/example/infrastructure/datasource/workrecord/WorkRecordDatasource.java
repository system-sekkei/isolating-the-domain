package example.infrastructure.datasource.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkRecordDatasource implements WorkRecordRepository {
    WorkRecordMapper mapper;

    @Override
    public void registerWorkRecord(TimeRecord timeRecord) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, timeRecord.employeeNumber(), timeRecord);
        mapper.deleteWorkTime(timeRecord.employeeNumber(), timeRecord.workDate());
        mapper.insertWorkTime(timeRecord.employeeNumber(), identifier, timeRecord);
    }

    @Override
    public TimeRecords findWorkRecords(EmployeeNumber employeeNumber, WorkMonth month) {
        List<TimeRecord> timeRecords = mapper.selectByMonth(employeeNumber, month.yyyyMM());
        return new TimeRecords(timeRecords);
    }

    WorkRecordDatasource(WorkRecordMapper mapper) {
        this.mapper = mapper;
    }
}
