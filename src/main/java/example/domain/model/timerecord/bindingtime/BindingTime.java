package example.domain.model.timerecord.bindingtime;

import example.domain.type.time.QuarterHour;

/**
 * 拘束時間
 */
public class BindingTime {

    QuarterHour value;

    public BindingTime(QuarterHour value) {
        this.value = value;
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
