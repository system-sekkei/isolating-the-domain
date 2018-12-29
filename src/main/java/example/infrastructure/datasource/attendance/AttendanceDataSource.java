package example.infrastructure.datasource.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.Attendances;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;

    @Override
    public void registerAttendance(Attendance attendance) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, attendance.workerNumber(), attendance);
        mapper.deleteWorkTime(attendance.workerNumber(), attendance.workDate());
        mapper.insertWorkTime(attendance.workerNumber(), identifier, attendance);
    }

    @Override
    public MonthlyAttendances findMonthly(WorkerNumber workerNumber, WorkMonth month) {
        List<Attendance> attendances = mapper.selectByMonth(workerNumber, month.yyyyMM());
        return new MonthlyAttendances(workerNumber, month, new Attendances(attendances));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
