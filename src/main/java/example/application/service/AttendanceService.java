package example.application.service;

import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.attendance.WorkTime;
import example.domain.model.attendance.WorkTimes;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AttendanceService {
    AttendanceRepository attendanceRepository;

    public void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime) {
        attendanceRepository.registerWorkTime(userId, workDay, workTime);
    }

    public TimeRecord findBy(UserIdentifier userId, DayOfMonth workDay) {
        TimeRecord ret = attendanceRepository.findBy(userId, workDay);
        return (ret == null) ? new TimeRecord() : ret;
    }

    public WorkTimes findMonthlyWorkTimes(UserIdentifier userId, YearMonth month) {
        return new WorkTimes(month.days().map(day -> new WorkTime(day, findBy(userId, day))));
    }

    AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
