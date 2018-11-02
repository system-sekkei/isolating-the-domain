package example.domain.model.payroll;

import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;

/**
 * 給与計算リポジトリ
 */
public interface PayrollRepository {
    void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime);
}
