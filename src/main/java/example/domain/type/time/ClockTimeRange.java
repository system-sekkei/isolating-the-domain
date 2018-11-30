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
        //XXX ここ良い方法は実装できない
        //Just Ideaですが、24時間を円と捉えて円弧の重なりみたいなロジック書けると
        //シンプルな気がするんだけどなぁ...
        if(dayStraddle() == other.dayStraddle()) {
            return intersect(this, other, 0);
        } else if(dayStraddle()){
            return intersectDouble(this, other);
        } else {
            return intersectDouble(other, this);
        }
    }

    private ClockTimeRange intersect(ClockTimeRange range1, ClockTimeRange range2, int supplyDays) {
        LocalDate now = LocalDate.now();
        LocalDateTime range1Begin = range1.beginDateTime(now);
        LocalDateTime range1End = range1.endDateTime(now);
        LocalDateTime range2Begin = range2.beginDateTime(now.plusDays(supplyDays));
        LocalDateTime range2End = range2.endDateTime(now.plusDays(supplyDays));
        if (range1Begin.compareTo(range2End) >= 0) return EMPTY_RANGE;
        if (range1End.compareTo(range2Begin) <= 0) return EMPTY_RANGE;

        ClockTime newBegin = range1Begin.compareTo(range2Begin) > 0 ? range1.begin : range2.begin;
        ClockTime newEnd = range1End.compareTo(range2End) < 0 ? range1.end : range2.end;
        return new ClockTimeRange(newBegin, newEnd);
    }

    private ClockTimeRange intersectDouble(ClockTimeRange straddleRange, ClockTimeRange nonStraddleRange) {
        //日跨ぎして無い方を矯正定期に1日進めてどちらか範囲が収まれば正とする
        for(int i = 0 ; i < 2 ; i++) {
            ClockTimeRange ret = intersect(straddleRange, nonStraddleRange, i);
            if(ret != EMPTY_RANGE) {
                return ret;
            }
        }
        return EMPTY_RANGE;
    }

    private boolean dayStraddle() {
        return begin.value.compareTo(end.value) > 0;
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