package example.domain.type.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 開始時刻と終了時刻を表現する(時刻間の時間間隔を返す)
 */
public class ClockTimeRange {

    ClockTime begin;
    ClockTime end;

    public ClockTimeRange(ClockTime begin, ClockTime end) {
        this.begin = begin;
        this.end = end;
    }

    public HourAndMinute between() {
        LocalDate now = LocalDate.now();
        long difference = ChronoUnit.MINUTES.between(beginDateTime(now), endDateTime(now));
        return HourAndMinute.from(new Minute(Math.toIntExact(difference)));
    }

    public ClockTime begin() {
        return begin;
    }

    public ClockTime end() {
        return end;
    }

    public ClockTimeRange intersect(ClockTimeRange other) {
        LocalDate now = LocalDate.now();
        LocalDateTime thisBegin = beginDateTime(now);
        LocalDateTime otherBegin = other.beginDateTime(now);
        LocalDateTime thisEnd = endDateTime(now);
        LocalDateTime otherEnd = other.endDateTime(now);
        if (thisBegin.compareTo(otherEnd) >= 0) return EMPTY_RANGE;
        if (thisEnd.compareTo(otherBegin) <= 0) return EMPTY_RANGE;

        ClockTime newBegin = thisBegin.compareTo(otherBegin) > 0 ? begin : other.begin;
        ClockTime newEnd = thisEnd.compareTo(otherEnd) < 0 ? end : other.end;
        return new ClockTimeRange(newBegin, newEnd);
    }

    public static ClockTimeRange EMPTY_RANGE = new ClockTimeRange(new ClockTime("0:00"), new ClockTime("0:00"));

    //日またぎの計算やねこいので内部系算用にLocalDateTimeを使う
    private LocalDateTime beginDateTime(LocalDate baseDate) {
        return LocalDateTime.of(baseDate, begin.value);
    }

    private LocalDateTime endDateTime(LocalDate baseDate) {
        return LocalDateTime.of(begin.value.compareTo(end.value) > 0 ? baseDate.plusDays(1L) : baseDate, end.value);
    }
}