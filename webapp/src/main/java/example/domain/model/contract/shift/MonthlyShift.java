package example.domain.model.contract.shift;

import example.domain.type.date.Month;

import java.util.List;

/**
 * 月間シフト
 */
public class MonthlyShift {
    Month month;
    List<WeeklyShift> weeklyShifts;
}
