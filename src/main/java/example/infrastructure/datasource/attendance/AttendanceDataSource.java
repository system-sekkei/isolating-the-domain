package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;

    @Override
    public void registerAttendance(WorkerIdentifier workerIdentifier, AttendanceOfDay attendanceOfDay) {
        Long identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTime(identifier, workerIdentifier, attendanceOfDay);
        mapper.deleteWorkTimeMapper(workerIdentifier, attendanceOfDay.date());
        mapper.insertWorkTimeMapper(identifier, workerIdentifier, attendanceOfDay.date());
    }

    AttendanceOfDay findBy(WorkerIdentifier workerIdentifier, Date workDay) {
        AttendanceOfDay attendanceOfDay = mapper.select(workerIdentifier, workDay);
        return (attendanceOfDay == null) ? new AttendanceOfDay(workDay) : attendanceOfDay;
    }

    @Override
    public MonthlyAttendances findMonthly(WorkerIdentifier workerIdentifier, YearMonth month) {
        return new MonthlyAttendances(month, month.days().stream()
                .map(day -> findBy(workerIdentifier, day)).collect(Collectors.toList()));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
