package example.infrastructure.datasource.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkRecords;
import example.domain.model.workrecord.WorkMonth;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;

    @Override
    public void registerAttendance(WorkRecord workRecord) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, workRecord.workerNumber(), workRecord);
        mapper.deleteWorkTime(workRecord.workerNumber(), workRecord.workDate());
        mapper.insertWorkTime(workRecord.workerNumber(), identifier, workRecord);
    }

    @Override
    public Attendance findMonthly(WorkerNumber workerNumber, WorkMonth month) {
        List<WorkRecord> workRecords = mapper.selectByMonth(workerNumber, month.yyyyMM());
        return new Attendance(workerNumber, month, new WorkRecords(workRecords));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
