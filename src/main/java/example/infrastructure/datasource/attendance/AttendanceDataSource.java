package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.MonthlyAttendances;
import example.application.repository.AttendanceRepository;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;

    @Override
    public void registerAttendance(WorkerNumber workerNumber, AttendanceOfDay attendanceOfDay) {
        Long identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTime(identifier, workerNumber, attendanceOfDay);
        mapper.deleteWorkTimeMapper(workerNumber, attendanceOfDay.date());
        mapper.insertWorkTimeMapper(identifier, workerNumber, attendanceOfDay.date());
    }

    AttendanceOfDay findBy(WorkerNumber workerNumber, Date workDay) {
        AttendanceOfDay attendanceOfDay = mapper.select(workerNumber, workDay);
        return (attendanceOfDay == null) ? new AttendanceOfDay(workDay) : attendanceOfDay;
    }

    @Override
    public MonthlyAttendances findMonthly(WorkerNumber workerNumber, YearMonth month) {
        return new MonthlyAttendances(month, month.days().stream()
                .map(day -> findBy(workerNumber, day)).collect(Collectors.toList()));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
