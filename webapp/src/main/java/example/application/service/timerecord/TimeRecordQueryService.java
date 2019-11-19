package example.application.service.timerecord;

import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
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
    public TimeRecord timeRecord(Employee employee, WorkDate workDate) {
        return timeRecordRepository.findTimeRecords(employee, WorkMonth.from(workDate)).at(workDate);
    }

    TimeRecordQueryService(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }
}
