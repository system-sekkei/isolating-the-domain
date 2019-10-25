package example.domain.model.timerecord.timefact;

/**
 * 勤務開始日時
 */
public class StartDateTime {

    StartDate startDate;
    StartTime startTime;

    @Deprecated
    StartDateTime() {
    }

    public StartDateTime(StartDate startDate, StartTime startTime) {
        this.startDate = startDate;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return startDate.toString() + " " + startTime.toString();
    }

}
