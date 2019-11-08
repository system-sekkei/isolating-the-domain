package example.domain.model.legislation;

import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.datetime.DateTime;
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
        DateTime earlyMorningFinishDateTime = new DateTime(LocalDateTime.of(range.start().date().value(), nightFinishTime.value()));
        DateTime nightStartDateTime = new DateTime(LocalDateTime.of(range.start().date().value(), nightStartTime.value()));
        DateTime nightFinishDateTime = new DateTime(LocalDateTime.of(range.start().date().plusDays(1).value(), nightFinishTime.value()));

        Minute minute = new Minute(0);
        // 早朝
        if (range.start().value().isBefore(earlyMorningFinishDateTime)) {
            if (range.end().value().isBefore(earlyMorningFinishDateTime)) {
                return DateTime.between(range.start().normalized().value(), range.end().normalized().value());
            }

            minute = minute.add(DateTime.between(range.start().normalized().value(), earlyMorningFinishDateTime));
        }
        // 残業
        if (range.end().value().isAfter(nightStartDateTime)) {
            if (range.end().value().isBefore(nightFinishDateTime)) {
                if (range.start().value().isAfter(nightStartDateTime)) {
                    minute = minute.add(DateTime.between(range.start().normalized().value(), range.end().normalized().value()));
                } else {
                    minute = minute.add(DateTime.between(nightStartDateTime, range.end().normalized().value()));
                }
            } else {
                minute = minute.add(DateTime.between(nightStartDateTime, nightFinishDateTime));
            }
        }
        return minute;
    }
}
