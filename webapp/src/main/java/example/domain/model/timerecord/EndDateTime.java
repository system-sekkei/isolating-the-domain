package example.domain.model.timerecord;

import example.domain.type.time.InputTime;

/**
 * 勤務終了日時
 */
public class EndDateTime {

    EndDate endDate;
    EndTime endTime;

    @Deprecated
    EndDateTime() {
    }

    public EndDateTime(WorkDate workDate, InputTime endTime) {
        this.endDate = endTime.isOverFlow() ? new EndDate(workDate.value.nextDay()) : new EndDate(workDate.value);
        this.endTime = new EndTime(endTime.toClockTime());
    }

    @Override
    public String toString() {
        return endDate.toString() + " " + endTime.toString();
    }

}
