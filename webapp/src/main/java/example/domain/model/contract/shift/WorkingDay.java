package example.domain.model.contract.shift;

import example.domain.type.date.Date;

/**
 * 勤務日
 */
public class WorkingDay {
    Date date;
    StartingTime startingTime;
    EndingTime endingTime;
    BreakTime breakTime;
}
