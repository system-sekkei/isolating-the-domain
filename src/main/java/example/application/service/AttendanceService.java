package example.application.service;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AttendanceService {
    AttendanceRepository attendanceRepository;

    public void registerWorkTime(UserIdentifier userId, AttendanceOfDay work) {
        attendanceRepository.registerWorkTime(userId, work);
    }

    public AttendanceOfDay findBy(UserIdentifier userId, Date workDay) {
        return attendanceRepository.findBy(userId, workDay);
    }

    public AttendanceOfMonth findMonthlyWorkTimes(UserIdentifier userId, YearMonth month) {
        return new AttendanceOfMonth(month.days().map(day -> findBy(userId, day)).collect(Collectors.toList()));
    }

    AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
