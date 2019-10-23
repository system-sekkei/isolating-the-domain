package example.domain.model.timerecord.timefact;

import example.domain.type.time.QuarterRoundClockTimeRange;

/**
 * 勤務の開始と終了
 */
public class WorkRange {

    StartDateTime startDateTime;
    EndDateTime endDateTime;

    @Deprecated
    WorkRange() {
    }

    public WorkRange(StartDateTime startDateTime, EndDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public QuarterRoundClockTimeRange quarterRoundClockTimeRange() {
        return new QuarterRoundClockTimeRange(startDateTime.startTime.normalizedClockTime(), endDateTime.endTime.normalizedClockTime());
    }

    public StartTime start() {
        return startDateTime.startTime;
    }

    public EndTime end() {
        return endDateTime.endTime;
    }

    public StartDate startDate() {
        return startDateTime.startDate;
    }

    public String endTimeText() {
        return endDateTime.endTimeTextWith(startDateTime.startDate);
    }
}
