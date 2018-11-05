package example.domain.model.attendance;

import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;

/**
 * 給与計算リポジトリ
 */
public interface AttendanceRepository {
    void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime);
    TimeRecord findBy(UserIdentifier userId, DayOfMonth workDay);
}
