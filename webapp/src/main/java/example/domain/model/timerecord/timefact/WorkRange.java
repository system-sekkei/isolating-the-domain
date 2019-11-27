package example.domain.model.timerecord.timefact;

import example.domain.BusinessLogic;
import example.domain.type.datetime.QuarterRoundDateTime;
import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

import javax.validation.constraints.AssertTrue;

/**
 * 勤務の開始と終了
 */
public class WorkRange {

    StartDateTime startDateTime;
    EndDateTime endDateTime;

    boolean workTimeValid;

    @Deprecated
    WorkRange() {
    }

    public WorkRange(StartDateTime startDateTime, EndDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public QuarterHour quarterHour() {
        Minute duration = QuarterRoundDateTime.between(startDateTime.normalized(), endDateTime.normalized());
        return new QuarterHour(duration);
    }

    public StartDateTime start() {
        return startDateTime;
    }

    public EndDateTime end() {
        return endDateTime;
    }

    public String endTimeText() {
        return endDateTime.endTimeTextWith(startDateTime);
    }

    public boolean isOverlap(WorkRange other) {
        return !notOverlap(other);
    }

    boolean notOverlap(WorkRange other) {
        return (startDateTime.isAfter(other.startDateTime) && startDateTime.isAfter(other.endDateTime))
                || (endDateTime.isBefore(other.startDateTime) && endDateTime.isBefore(other.endDateTime));
    }

    @AssertTrue(message = "終了時刻には開始時刻よりあとの時刻を入力してください", groups = BusinessLogic.class)
    public boolean isWorkTimeValid() {
        return endDateTime.isAfter(startDateTime);
    }
}
