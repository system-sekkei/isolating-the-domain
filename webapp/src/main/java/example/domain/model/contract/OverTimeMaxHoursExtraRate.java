package example.domain.model.contract;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 時間外割増率
 */
public class OverTimeMaxHoursExtraRate {
    ExtraPayRate value;

    public OverTimeMaxHoursExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverTimeMaxHoursExtraRate legal() {
        return new OverTimeMaxHoursExtraRate(50);
    }
}
