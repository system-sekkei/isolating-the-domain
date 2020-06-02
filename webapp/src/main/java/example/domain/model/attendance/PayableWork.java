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

    public OverLegalMoreThan60HoursWorkTime overLegalMoreThan60HoursWorkTime(Attendance attendance) {
        return actualWorkDateTime.overLegalMoreThan60HoursWorkTime(attendance);
    }

    public OverLegalWithin60HoursWorkTime overLegalWithin60HoursWorkTime(Attendance attendance) {
        return actualWorkDateTime.overLegalWithin60HoursWorkTime(attendance);
    }

    public LegalDaysOffWorkTime legalDaysOffWorkTime(Attendance attendance) {
        return actualWorkDateTime.legalDaysOffWorkTime(attendance);
    }
}
