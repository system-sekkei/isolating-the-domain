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

    EndDateTime(EndDate endDate, EndTime endTime) {
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public static EndDateTime from(WorkDate workDate, InputTime inputTime) {
        EndDate endDate = inputTime.isOverFlow() ? new EndDate(workDate.value.nextDay()) : new EndDate(workDate.value);
        EndTime endTime = new EndTime(inputTime.toClockTime());
        return new EndDateTime(endDate, endTime);
    }

    @Override
    public String toString() {
        return endDate.toString() + " " + endTime.toString();
    }

}
