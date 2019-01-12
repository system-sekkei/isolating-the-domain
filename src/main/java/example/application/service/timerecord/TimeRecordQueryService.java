package example.application.service.timerecord;

import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Service;

/**
 * 勤務実績参照サービス
 */
@Service
public class TimeRecordQueryService {

    TimeRecordRepository timeRecordRepository;

    /**
     * 日次勤務実績取得
     */
    public TimeRecord timeRecord(EmployeeNumber employeeNumber, WorkDate workDate) {
        return timeRecordRepository.findTimeRecords(employeeNumber, WorkMonth.from(workDate)).at(workDate);
    }

    TimeRecordQueryService(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }
}
