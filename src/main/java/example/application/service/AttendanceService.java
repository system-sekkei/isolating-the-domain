package example.application.service;

import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    AttendanceRepository attendanceRepository;

    public void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime) {
        attendanceRepository.registerWorkTime(userId, workDay, workTime);
    }

    public TimeRecord findBy(UserIdentifier userId, DayOfMonth workDay) {
        return attendanceRepository.findBy(userId, workDay);
    }

    AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
