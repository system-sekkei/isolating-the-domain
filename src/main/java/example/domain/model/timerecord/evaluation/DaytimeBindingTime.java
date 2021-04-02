package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 日中拘束時間
 */
public class DaytimeBindingTime {

    QuarterHour value;

    public DaytimeBindingTime(BindingTime bindingTime, NightBindingTime nightBindingTime) {
        this.value = bindingTime.quarterHour().subtract(nightBindingTime.quarterHour());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    DaytimeWorkTime subtract(DaytimeBreakTime daytimeBreakTime) {
        QuarterHour quarterHour = quarterHour().subtract(daytimeBreakTime.quarterHourRoundUp());
        return new DaytimeWorkTime(quarterHour);
    }
}
