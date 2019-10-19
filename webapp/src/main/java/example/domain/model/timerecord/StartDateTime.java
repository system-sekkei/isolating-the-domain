package example.domain.model.timerecord;


/**
 * 勤務開始日時
 */
public class StartDateTime {

    StartDate startDate;
    StartTime startTime;

    @Deprecated
    StartDateTime() {
    }

    public StartDateTime(WorkDate workDate, StartTime startTime) {
        this.startDate = new StartDate(workDate.value);
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return startDate.toString() + " " + startTime.toString();
    }

}
