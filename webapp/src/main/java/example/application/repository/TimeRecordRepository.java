package example.application.repository;

import example.domain.model.timerecord.evaluation.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.TimeRecord;

/**
 * 勤務実績リポジトリ
 */
public interface TimeRecordRepository {

    void registerTimeRecord(TimeRecord timeRecord);

    TimeRecords findTimeRecords(Employee employee, WorkMonth month);
}
