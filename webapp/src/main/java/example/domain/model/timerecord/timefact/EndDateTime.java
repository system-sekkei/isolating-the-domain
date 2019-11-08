package example.domain.model.timerecord.timefact;

import example.domain.type.datetime.DateTime;
import example.domain.type.datetime.QuarterRoundDateTime;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

/**
 * 勤務終了日時
 */
public class EndDateTime {

    DateTime value;

    @Deprecated
    EndDateTime() {
    }

    public EndDateTime(DateTime value) {
        this.value = value;
    }

    public boolean isAfter(StartDateTime startDateTime) {
        if (value.date().isAfter(startDateTime.value.date())) return true;

        return value.time().isAfter(startDateTime.value.time());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String endTimeTextWith(StartDateTime startDateTime) {
        Period period = startDateTime.value.date().value().until(value.date().value());
        return clockTimeTextOverDays(period.getDays());
    }

    String clockTimeTextOverDays(int days) {
        LocalTime value = this.value.time().value();
        Duration duration = Duration.ofDays(days)
                .plusHours(value.getHour())
                .plusMinutes(value.getMinute());

        return String.format("%02d:%02d",
                duration.toHours(),
                // duration.toMinutesPart() はJava9から
                duration.toMinutes() % 60
        );
    }

    public QuarterRoundDateTime normalized() {
        return value.quarterRoundDown();
    }

    public DateTime value() {
        return value;
    }
}
