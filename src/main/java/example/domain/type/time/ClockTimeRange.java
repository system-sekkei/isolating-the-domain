package example.domain.type.time;

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

    public Minute between() {
        return begin.betweenMinute(end);
    }

    public ClockTime begin() {
        return begin;
    }

    public ClockTime end() {
        return end;
    }
}