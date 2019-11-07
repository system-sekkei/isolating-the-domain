package example.application.repository;

import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.employee.Employee;

/**
 * 勤務実績リポジトリ
 */
public interface TimeRecordRepository {

    void registerTimeRecord(TimeRecord timeRecord);

    TimeRecords findTimeRecords(Employee employee, WorkMonth month);
}
