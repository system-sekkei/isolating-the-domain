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
    public void registerAttendance(WorkerAttendance workerAttendance) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, workerAttendance.workerNumber(), workerAttendance.attendance());
        mapper.deleteWorkTime(workerAttendance.workerNumber(), workerAttendance.attendance().workDay());
        mapper.insertWorkTime(workerAttendance.workerNumber(), identifier, workerAttendance.attendance());
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
