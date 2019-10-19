package example.domain.model.timerecord;

import example.domain.type.time.ClockTime;

/**
 * 勤務終了日時
 */
public class EndDateTime {

    EndDate endDate;
    EndTime endTime;

    @Deprecated
    EndDateTime() {
    }

    public EndDateTime(WorkDate workDate, Integer endHour, Integer endMinute) {
        this.endDate = endHour > 23 ? new EndDate(workDate.value.nextDay()) : new EndDate(workDate.value);
        this.endTime = new EndTime(new ClockTime(endHour % 24, endMinute));
    }

    @Override
    public String toString() {
        return endDate.toString() + " " + endTime.toString();
    }

}
