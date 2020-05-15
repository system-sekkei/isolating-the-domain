package example.domain.model.contract.shift;

import example.domain.type.date.Week;

import java.util.List;

/**
 * 週間シフト
 */
public class WeeklyShift {
    Week week;
    List<WorkingDay> workingDays;
}
