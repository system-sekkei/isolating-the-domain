package example.infrastructure.datasource.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.Attendances;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.DateRange;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;

    @Override
    public void registerAttendance(WorkerNumber workerNumber, Attendance attendance) {
        Integer identifier = mapper.newWorkTimeIdentifier();
        mapper.insertWorkTimeHistory(identifier, workerNumber, attendance);
        mapper.deleteWorkTime(workerNumber, attendance.date());
        mapper.insertWorkTime(workerNumber, identifier, attendance);
    }

    Attendance findBy(WorkerNumber workerNumber, Date workDay) {
        Attendance attendance = mapper.select(workerNumber, workDay);
        return (attendance == null) ? new Attendance(workDay) : attendance;
    }

    @Override
    public MonthlyAttendances findMonthly(WorkerNumber workerNumber, YearMonth month) {
        return new MonthlyAttendances(month, new Attendances(month.days().stream()
                .map(day -> findBy(workerNumber, day)).collect(Collectors.toList())));
    }

    @Override
    public Attendances getAttendances(WorkerNumber workerNumber, Date startDate, Date endDate) {
        DateRange range = new DateRange(startDate, endDate);
        return new Attendances(range.days().stream()
                .map(day -> findBy(workerNumber, day)).collect(Collectors.toList()));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
