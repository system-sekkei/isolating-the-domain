package example.domain.model.attendance;

import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;

/**
 * 給与計算リポジトリ
 */
public interface AttendanceRepository {
    void registerWorkTime(UserIdentifier userId, Date workDay, TimeRecord workTime);
    TimeRecord findBy(UserIdentifier userId, Date workDay);
}
