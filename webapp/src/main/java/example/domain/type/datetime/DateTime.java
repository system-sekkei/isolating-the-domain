package example.domain.type.datetime;

import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * 日時
 */
public class DateTime {

    LocalDateTime value;

    @Deprecated
    DateTime() {
    }

    public DateTime(LocalDateTime value) {
        this.value = value;
    }

    public static DateTime parse(String date, String time) {
        LocalDate d = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime t = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:m"));
        return new DateTime(LocalDateTime.of(d, t));
    }

    public static DateTime parse(String date, String hour, String minute) {
        return parse(date, hour + ":" + minute);
    }

    public Date date() {
        return new Date(value.toLocalDate());
    }

    public ClockTime time() {
        return new ClockTime(value.toLocalTime());
    }

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(value);
    }

    public static Minute between(DateTime start, DateTime end) {
        Duration duration = Duration.between(start.value, end.value);
        return new Minute((int) duration.toMinutes());
    }

    public static Minute between(DateTime start, QuarterRoundDateTime end) {
        return between(start, end.value);
    }

    public QuarterRoundDateTime quarterRoundDown() {
        int minute = value.getMinute();
        if (minute < 15) {
            return new QuarterRoundDateTime(new DateTime(value.withMinute(0)));
        }
        if (minute < 30) {
            return new QuarterRoundDateTime(new DateTime(value.withMinute(15)));
        }
        if (minute < 45) {
            return new QuarterRoundDateTime(new DateTime(value.withMinute(30)));
        }
        return new QuarterRoundDateTime(new DateTime(value.withMinute(45)));
    }

    public QuarterRoundDateTime quarterRoundUp() {
        int minute = value.getMinute();
        if (minute == 0) {
            return new QuarterRoundDateTime(new DateTime(value));
        }
        if (minute <= 15) {
            return new QuarterRoundDateTime(new DateTime(value.withMinute(15)));
        }
        if (minute <= 30) {
            return new QuarterRoundDateTime(new DateTime(value.withMinute(30)));
        }
        if (minute <= 45) {
            return new QuarterRoundDateTime(new DateTime(value.withMinute(45)));
        }
        return new QuarterRoundDateTime(new DateTime(value.plusHours(1).withMinute(0)));
    }

    public boolean isAfter(DateTime other) {
        return value.isAfter(other.value);
    }

    public boolean isBefore(DateTime other) {
        return value.isBefore(other.value);
    }
}
