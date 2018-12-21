package example.infrastructure.datasource.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;

    @Override
    public void registerAttendance(WorkerNumber workerNumber, Attendance attendance) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, workerNumber, attendance);
        mapper.deleteWorkTime(workerNumber, attendance.workDay());
        mapper.insertWorkTime(workerNumber, identifier, attendance);
    }

    @Override
    public MonthlyAttendances findMonthly(WorkerNumber workerNumber, WorkMonth month) {
        List<Attendance> attendances = mapper.selectByMonth(workerNumber, month);
        return new MonthlyAttendances(workerNumber, month, new Attendances(attendances));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
