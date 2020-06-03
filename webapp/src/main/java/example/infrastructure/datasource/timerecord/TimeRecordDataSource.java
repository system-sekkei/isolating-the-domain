package example.infrastructure.datasource.timerecord;

import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.TimeRecords;
import example.domain.model.timerecord.evaluation.WeeklyTimeRecord;
import example.domain.model.timerecord.evaluation.WeeklyTimeRecords;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TimeRecordDataSource implements TimeRecordRepository {
    TimeRecordMapper mapper;

    @Override
    public void registerTimeRecord(TimeRecord timeRecord) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        EmployeeNumber employeeNumber = timeRecord.employeeNumber();
        mapper.insertWorkTimeHistory(identifier, employeeNumber, timeRecord, timeRecord.workDate());
        mapper.deleteWorkTime(employeeNumber, timeRecord.workDate());
        mapper.insertWorkTime(employeeNumber, identifier, timeRecord, timeRecord.workDate());
    }

    @Override
    public WeeklyTimeRecords findTimeRecords(Employee employee, WorkMonth month) {
        List<String> months = List.of(month.before().yyyyMM(), month.yyyyMM(), month.after().yyyyMM());
        List<TimeRecord> timeRecords = mapper.selectByMonths(employee.employeeNumber(), months);

        List<WeeklyTimeRecord> weeklyTimeRecords = timeRecords.stream()
                .filter(timeRecord -> timeRecord.workDate().isContainWeekOfMonth(month))
                .collect(Collectors.groupingBy(timeRecord -> timeRecord.workDate().weekNumber()))
                .values().stream()
                .map(weeklyRecords -> new WeeklyTimeRecord(new TimeRecords(weeklyRecords)))
                .collect(Collectors.toList());

        return new WeeklyTimeRecords(weeklyTimeRecords);
    }

    TimeRecordDataSource(TimeRecordMapper mapper) {
        this.mapper = mapper;
    }
}
