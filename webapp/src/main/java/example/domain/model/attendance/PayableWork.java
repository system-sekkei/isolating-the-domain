package example.domain.model.attendance;

import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
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

    public QuarterHour legalDaysOffWorkTime() {
        return actualWorkDateTime.legalDaysOffWorkTime().quarterHour();
    }

    public QuarterHour overLegalMoreThan60HoursWorkTime() {
        return actualWorkDateTime.overLegalMoreThan60HoursWorkTime().quarterHour();
    }

    public QuarterHour overLegalWithin60HoursWorkTime() {
        return actualWorkDateTime.overLegalWithin60HoursWorkTime().quarterHour();
    }
}
