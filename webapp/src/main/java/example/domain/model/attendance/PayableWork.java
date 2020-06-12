package example.domain.model.attendance;

import example.domain.model.timerecord.evaluation.*;
import example.domain.type.time.QuarterHour;

import java.time.LocalDate;

/**
 * 支払い対象となる稼働
 */
public class PayableWork {

    ActualWorkDateTime actualWorkDateTime;

    public PayableWork(ActualWorkDateTime actualWorkDateTime) {
        this.actualWorkDateTime = actualWorkDateTime;
    }

    public LocalDate date() {
        return actualWorkDateTime.workDate().toDate();
    }

    public QuarterHour workTime() {
        return actualWorkDateTime.workTime().quarterHour();
    }

    public QuarterHour nightWorkTime() {
        return actualWorkDateTime.nightWorkTime().quarterHour();
    }

    public OverLegalHoursWorkTime overLegalHoursWorkTime(WeeklyTimeRecord weeklyTimeRecord) {
        return actualWorkDateTime.overLegalHoursWorkTime(weeklyTimeRecord);
    }

    public LegalDaysOffWorkTime legalDaysOffWorkTime(WeeklyTimeRecord weeklyTimeRecord) {
        return actualWorkDateTime.legalDaysOffWorkTime(weeklyTimeRecord);
    }
}
