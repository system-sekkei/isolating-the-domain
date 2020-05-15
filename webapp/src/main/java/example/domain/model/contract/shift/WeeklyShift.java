package example.domain.model.contract.shift;

import example.domain.type.date.Date;
import example.domain.type.date.Week;

import java.util.List;

/**
 * 週間シフト
 */
public class WeeklyShift {
    Week week;
    List<WorkingDay> workingDays;

    public DayStatus dayStatus(Date date) {
        // TODO: 指定の日の種別(労働日・休日)を判定して返却
        return DayStatus.労働日;
    }
}
