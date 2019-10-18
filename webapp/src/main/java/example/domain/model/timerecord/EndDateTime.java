package example.domain.model.timerecord;


/**
 * 勤務終了日時
 */
public class EndDateTime {

    EndDate endDate;
    EndTime endTime;

    @Deprecated
    EndDateTime() {
    }

    public EndDateTime(EndDate endDate, EndTime endTime) {
        this.endDate = endDate;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return endDate.toString() + " " + endTime.toString();
    }

}
