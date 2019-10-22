package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 勤務時間
 */
public class WorkTime {

    QuarterHour value;

    public WorkTime(DaytimeWorkTime daytimeWorkTime, NightWorkTime nightWorkTime) {
        this.value = daytimeWorkTime.quarterHour().add(nightWorkTime.quarterHour());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
