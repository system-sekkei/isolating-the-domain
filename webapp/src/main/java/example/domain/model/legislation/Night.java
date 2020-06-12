package example.domain.model.legislation;

import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;
import example.domain.type.datetime.QuarterRoundDateTime;
import example.domain.type.time.Minute;
import example.domain.type.time.Time;

import java.time.LocalDateTime;

/**
 * 深夜
 */
public class Night {

    Time nightStartTime;
    Time nightFinishTime;

    public Night(Time nightStartTime, Time nightFinishTime) {
        this.nightStartTime = nightStartTime;
        this.nightFinishTime = nightFinishTime;
    }

    public static Night legal() {
        // 第三十七条第四項で定められている深夜
        return new Night(new Time("22:00"), new Time("05:00"));
    }

    public Minute nightMinute(QuarterRoundDateTime startDateTime, QuarterRoundDateTime endDateTime) {
        Minute before = before(startDateTime, endDateTime);
        Minute after = after(startDateTime, endDateTime);
        return before.add(after);
    }

    private Minute before(QuarterRoundDateTime startDateTime, QuarterRoundDateTime endDateTime) {
        DateTime earlyMorningFinishDateTime = new DateTime(LocalDateTime.of(startDateTime.value().date().value, nightFinishTime.value()));

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

    private Minute after(QuarterRoundDateTime startDateTime, QuarterRoundDateTime endDateTime) {
        DateTime nightStartDateTime = new DateTime(LocalDateTime.of(startDateTime.value().date().value, nightStartTime.value()));
        DateTime nightFinishDateTime = new DateTime(LocalDateTime.of(new Date(startDateTime.value().date().value.plusDays(1)).value, nightFinishTime.value()));

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
