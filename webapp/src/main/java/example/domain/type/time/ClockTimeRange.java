package example.domain.type.time;

import java.time.Duration;

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

    public boolean across2days() {
        return begin.isAfter(end);
    }

    public Minute minute() {
        Minute minute = begin.betweenMinute(end);
        if (across2days()) {
            return minute.add(new Minute((int) Duration.ofDays(1).toMinutes()));
        }
        return minute;
    }

    public ClockTime begin() {
        return begin;
    }

    public ClockTime end() {
        return end;
    }
}