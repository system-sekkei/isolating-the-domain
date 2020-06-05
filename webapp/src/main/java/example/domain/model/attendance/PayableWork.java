package example.domain.model.attendance;

import example.domain.model.timerecord.evaluation.*;
import example.domain.type.date.Date;
import example.domain.type.time.QuarterHour;

/**
 * 支払い対象となる稼働
 */
public class PayableWork {

    ActualWorkDateTime actualWorkDateTime;

    public PayableWork(ActualWorkDateTime actualWorkDateTime) {
        this.actualWorkDateTime = actualWorkDateTime;
    }

    public Date date() {
        return actualWorkDateTime.workDate().toDate();
    }

    public QuarterHour workTime() {
        return actualWorkDateTime.workTime().quarterHour();
    }

    public QuarterHour nightWorkTime() {
        return actualWorkDateTime.nightWorkTime().quarterHour();
    }

    public OverLegalMoreThan60HoursWorkTime overLegalMoreThan60HoursWorkTime(WeeklyTimeRecord weeklyTimeRecord) {
        return actualWorkDateTime.overLegalMoreThan60HoursWorkTime(weeklyTimeRecord);
    }

    public OverLegalWithin60HoursWorkTime overLegalWithin60HoursWorkTime(WeeklyTimeRecord weeklyTimeRecord) {
        return actualWorkDateTime.overLegalWithin60HoursWorkTime(weeklyTimeRecord);
    }

    public LegalDaysOffWorkTime legalDaysOffWorkTime(WeeklyTimeRecord weeklyTimeRecord) {
        return actualWorkDateTime.legalDaysOffWorkTime(weeklyTimeRecord);
    }
}
