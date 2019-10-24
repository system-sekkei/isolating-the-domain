package example.infrastructure.datasource.timerecord;

import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimeRecordDataSource implements TimeRecordRepository {
    TimeRecordMapper mapper;

    @Override
    public void registerTimeRecord(TimeRecord timeRecord) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, timeRecord.employeeNumber(), timeRecord);
        mapper.deleteWorkTime(timeRecord.employeeNumber(), timeRecord.actualWorkDateTime().workRange().startDate());
        mapper.insertWorkTime(timeRecord.employeeNumber(), identifier, timeRecord);
    }

    @Override
    public TimeRecords findTimeRecords(EmployeeNumber employeeNumber, WorkMonth month) {
        List<TimeRecord> timeRecords = mapper.selectByMonth(employeeNumber, month.yyyyMM());
        return new TimeRecords(timeRecords);
    }

    TimeRecordDataSource(TimeRecordMapper mapper) {
        this.mapper = mapper;
    }
}
