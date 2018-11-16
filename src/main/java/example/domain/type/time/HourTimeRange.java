package example.domain.type.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 開始時刻と終了時刻を表現する(時刻間の時間間隔を返す)
 */
public class HourTimeRange {

    HourTime begin;
    HourTime end;

    public HourTimeRange(HourTime begin, HourTime end) {
        this.begin = begin;
        this.end = end;
    }

    public HourAndMinute between() {
        LocalDate now = LocalDate.now();
        long difference = ChronoUnit.MINUTES.between(beginDateTime(now), endDateTime(now));
        return HourAndMinute.from(new Minute(Math.toIntExact(difference)));
    }

    public HourTimeRange intersect(HourTimeRange other) {
        LocalDate now = LocalDate.now();
        HourTime newBegin = beginDateTime(now).compareTo(other.beginDateTime(now)) > 0 ? begin : other.begin;
        HourTime newEnd = endDateTime(now).compareTo(other.endDateTime(now)) < 0 ? end : other.end;
        return new HourTimeRange(newBegin, newEnd);
    }

    //日またぎの計算やねこいので内部系算用にLocalDateTimeを使う
    private LocalDateTime beginDateTime(LocalDate baseDate) {
        return LocalDateTime.of(baseDate, begin.value);
    }

    private LocalDateTime endDateTime(LocalDate baseDate) {
        return LocalDateTime.of(begin.value.compareTo(end.value) > 0 ? baseDate.plusDays(1L) : baseDate, end.value);
    }
}