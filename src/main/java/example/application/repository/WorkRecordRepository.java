package example.application.repository;

import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.employee.EmployeeNumber;

/**
 * 勤務実績リポジトリ
 */
public interface WorkRecordRepository {

    void registerWorkRecord(TimeRecord timeRecord);

    TimeRecords findWorkRecords(EmployeeNumber employeeNumber, WorkMonth month);
}
