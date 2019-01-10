package example.application.service.workrecord;

import example.application.repository.WorkRecordRepository;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Service;

/**
 * 勤務実績参照サービス
 */
@Service
public class WorkRecordQueryService {

    WorkRecordRepository workRecordRepository;

    /**
     * 日次勤務実績取得
     */
    public TimeRecord workRecord(EmployeeNumber employeeNumber, WorkDate workDate) {
        return workRecordRepository.findWorkRecords(employeeNumber, WorkMonth.from(workDate)).at(workDate);
    }

    WorkRecordQueryService(WorkRecordRepository workRecordRepository) {
        this.workRecordRepository = workRecordRepository;
    }
}
