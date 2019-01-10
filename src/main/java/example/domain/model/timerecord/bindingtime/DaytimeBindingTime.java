package example.domain.model.timerecord.bindingtime;

import example.domain.type.time.QuarterHour;

/**
 * 日中拘束時間
 */
public class DaytimeBindingTime {

    QuarterHour value;

    public DaytimeBindingTime(BindingTime bindingTime, MidnightBindingTime midnightBindingTime) {
        this.value = bindingTime.quarterHour().subtract(midnightBindingTime.quarterHour());
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
