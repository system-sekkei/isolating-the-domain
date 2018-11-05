package example.domain.model.attendance;

import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {
    void registerWorkTime(UserIdentifier userId, AttendanceOfDay work);
    AttendanceOfDay findBy(UserIdentifier userId, Date workDay);

    AttendanceOfMonth findMonthly(UserIdentifier userId, YearMonth month);
}
