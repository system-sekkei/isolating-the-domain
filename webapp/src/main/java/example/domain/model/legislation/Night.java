package example.domain.model.legislation;

import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.Minute;

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

    public Minute nightMinute(ClockTimeRange range) {
        return startTimeToMidnightMinute(range).add(midnightToFinishTimeMinute(range));
    }

    private Minute startTimeToMidnightMinute(ClockTimeRange range) {
        if (range.end().isBefore(nightStartTime)) {
            return new Minute(0);
        }
        if (range.begin().isBefore(nightStartTime)) {
            return new ClockTimeRange(nightStartTime, range.end()).between();
        }
        // 開始終了とも深夜
        return new ClockTimeRange(range.begin(), range.end()).between();
    }

    private Minute midnightToFinishTimeMinute(ClockTimeRange range) {
        if (range.begin().isAfter(nightFinishTime)) {
            return new Minute(0);
        }
        if (range.end().isAfter(nightFinishTime)) {
            return new ClockTimeRange(range.begin(), nightFinishTime).between();
        }
        // 開始終了とも深夜（早朝）
        return new ClockTimeRange(range.begin(), range.end()).between();
    }
}
