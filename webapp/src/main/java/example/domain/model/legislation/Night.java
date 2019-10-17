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
        if (range.across2days()) {
            return new ClockTimeRange(
                    ClockTime.later(range.begin(), nightStartTime),
                    ClockTime.faster(range.end(), nightFinishTime)
            ).minute();
        }

        Minute minute = new Minute(0);
        // 早朝
        if (range.begin().isBefore(nightFinishTime)) {
            minute = minute.add(new ClockTimeRange(range.begin(), ClockTime.faster(range.end(), nightFinishTime)).minute());
        }
        // 残業
        if (range.end().isAfter(nightStartTime)) {
            minute = minute.add(new ClockTimeRange(nightStartTime, range.end()).minute());
        }
        return minute;
    }
}
