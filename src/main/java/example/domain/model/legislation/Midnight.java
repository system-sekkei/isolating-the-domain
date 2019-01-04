package example.domain.model.legislation;

import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.Minute;

/**
 * 深夜
 */
public class Midnight {

    ClockTime midnightFinishTime;
    ClockTime midnightStartTime;

    public Midnight(ClockTime midnightFinishTime, ClockTime midnightStartTime) {
        this.midnightFinishTime = midnightFinishTime;
        this.midnightStartTime = midnightStartTime;
    }

    public static Midnight legal() {
        // 第三十七条第四項で定められている深夜
        return new Midnight(new ClockTime("05:00"), new ClockTime("22:00"));
    }

    public Minute midnightMinute(ClockTimeRange range) {
        return calculate1(range).add(calculate2(range));
    }

    private Minute calculate1(ClockTimeRange range) {
        // 深夜開始から24時までの分を計算
        if (range.end().isBefore(midnightStartTime)) {
            return new Minute(0);
        }
        if (range.begin().isBefore(midnightStartTime)) {
            return new ClockTimeRange(midnightStartTime, range.end()).between();
        }
        // 開始終了とも深夜
        return new ClockTimeRange(range.begin(), range.end()).between();
    }

    private Minute calculate2(ClockTimeRange range) {
        // 0時から深夜終了までの分を計算
        if (range.begin().isAfter(midnightFinishTime)) {
            return new Minute(0);
        }
        if (range.end().isAfter(midnightFinishTime)) {
            return new ClockTimeRange(range.begin(), midnightFinishTime).between();
        }
        // 開始終了とも深夜（早朝）
        return new ClockTimeRange(range.begin(), range.end()).between();
    }
}
