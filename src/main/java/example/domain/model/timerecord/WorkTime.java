package example.domain.model.timerecord;

import example.domain.type.time.QuarterHour;

/**
 * 勤務時間
 */
public class WorkTime {

    QuarterHour value;

    public WorkTime(DaytimeWorkTime daytimeWorkTime, MidnightWorkTime midnightWorkTime) {
        this.value = daytimeWorkTime.quarterHour().add(midnightWorkTime.quarterHour());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
