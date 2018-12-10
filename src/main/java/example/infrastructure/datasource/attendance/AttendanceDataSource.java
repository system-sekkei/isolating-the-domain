package example.infrastructure.datasource.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.DateRange;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    Attendance findBy(WorkerNumber workerNumber, WorkDay workDay) {
        Attendance attendance = mapper.select(workerNumber, workDay);
        return (attendance == null) ? new Attendance(workDay) : attendance;
    }

    @Override
    public MonthlyAttendances findMonthly(WorkerNumber workerNumber, WorkMonth month) {
        return new MonthlyAttendances(month, new Attendances(month.days().stream()
                .map(day -> findBy(workerNumber, day)).collect(Collectors.toList())));
    }

    @Override
    public Attendances getAttendances(WorkerNumber workerNumber, Date startDate, Date endDate) {
        DateRange range = new DateRange(startDate, endDate);
        Stream<WorkDay> workDayStream = range.days().stream().map(date -> new WorkDay(LocalDate.of(date.year().value(), date.month().value(), date.dayOfMonth())));

        return new Attendances(workDayStream.map(day -> findBy(workerNumber, day))
                .collect(Collectors.toList()));
    }

    AttendanceDataSource(AttendanceMapper mapper) {
        this.mapper = mapper;
    }
}
