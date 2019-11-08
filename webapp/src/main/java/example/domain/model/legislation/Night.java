package example.domain.model.legislation;

import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.datetime.DateTime;
import example.domain.type.datetime.QuarterRoundDateTime;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import java.time.LocalDateTime;

/**
 * 深夜
 */
public class Night {

    ClockTime nightStartTime;
    ClockTime nightFinishTime;

    public Night(ClockTime nightStartTime, ClockTime nightFinishTime) {
        this.nightStartTime = nightStartTime;
        this.nightFinishTime = nightFinishTime;
    }

    public static Night legal() {
        // 第三十七条第四項で定められている深夜
        return new Night(new ClockTime("22:00"), new ClockTime("05:00"));
    }

    public Minute nightMinute(WorkRange range) {
        Minute earlyMorning = earlyMorning(range.start().normalized(), range.end().normalized());
        Minute midnight = midnight(range.start().normalized(), range.end().normalized());
        return earlyMorning.add(midnight);
    }

    private Minute earlyMorning(QuarterRoundDateTime startDateTime, QuarterRoundDateTime endDateTime) {
        DateTime earlyMorningFinishDateTime = new DateTime(LocalDateTime.of(startDateTime.value().date().value(), nightFinishTime.value()));

        if (startDateTime.isBefore(earlyMorningFinishDateTime)
            && endDateTime.isAfter(earlyMorningFinishDateTime)) {
            return QuarterRoundDateTime.between(startDateTime, earlyMorningFinishDateTime);
        }

        if (startDateTime.isBefore(earlyMorningFinishDateTime)
            && endDateTime.isBefore(earlyMorningFinishDateTime)) {
            return QuarterRoundDateTime.between(startDateTime, endDateTime);
        }

        return new Minute(0);
    }

    private Minute midnight(QuarterRoundDateTime startDateTime, QuarterRoundDateTime endDateTime) {
        DateTime nightStartDateTime = new DateTime(LocalDateTime.of(startDateTime.value().date().value(), nightStartTime.value()));
        DateTime nightFinishDateTime = new DateTime(LocalDateTime.of(startDateTime.value().date().plusDays(1).value(), nightFinishTime.value()));

        if (endDateTime.isAfter(nightStartDateTime)
            && endDateTime.isBefore(nightFinishDateTime)
            && startDateTime.isAfter(nightStartDateTime)) {
            return QuarterRoundDateTime.between(startDateTime, endDateTime);
        }

        if (endDateTime.isAfter(nightStartDateTime)
            && endDateTime.isBefore(nightFinishDateTime)) {
            return DateTime.between(nightStartDateTime, endDateTime);
        }

        if (endDateTime.isAfter(nightStartDateTime)) {
            return DateTime.between(nightStartDateTime, nightFinishDateTime);
        }

        return new Minute(0);
    }
}
