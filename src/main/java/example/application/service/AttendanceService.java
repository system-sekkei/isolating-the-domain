package example.application.service;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AttendanceService {
    AttendanceRepository attendanceRepository;

    public void registerWorkTime(UserIdentifier userId, Date workDay, TimeRecord workTime) {
        attendanceRepository.registerWorkTime(userId, workDay, workTime);
    }

    public TimeRecord findBy(UserIdentifier userId, Date workDay) {
        TimeRecord ret = attendanceRepository.findBy(userId, workDay);
        return (ret == null) ? new TimeRecord() : ret;
    }

    public AttendanceOfMonth findMonthlyWorkTimes(UserIdentifier userId, YearMonth month) {
        return new AttendanceOfMonth(month.days().map(day -> new AttendanceOfDay(day, findBy(userId, day))).collect(Collectors.toList()));
    }

    AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
