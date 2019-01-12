package example.application.repository;

import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.employee.EmployeeNumber;

/**
 * 勤務実績リポジトリ
 */
public interface TimeRecordRepository {

    void registerTimeRecord(TimeRecord timeRecord);

    TimeRecords findTimeRecords(EmployeeNumber employeeNumber, WorkMonth month);
}
