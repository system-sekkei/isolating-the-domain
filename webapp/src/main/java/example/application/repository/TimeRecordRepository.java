package example.application.repository;

import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WeeklyTimeRecords;

/**
 * 勤務実績リポジトリ
 */
public interface TimeRecordRepository {

    void registerTimeRecord(TimeRecord timeRecord);

    WeeklyTimeRecords findTimeRecords(Employee employee, WorkMonth month);
}
