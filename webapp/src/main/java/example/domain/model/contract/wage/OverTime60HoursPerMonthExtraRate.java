package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 月60時間超えの時間外労働 時間外割増率
 */
public class OverTime60HoursPerMonthExtraRate {
    ExtraPayRate value;

    public OverTime60HoursPerMonthExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverTime60HoursPerMonthExtraRate regulation() {
        return new OverTime60HoursPerMonthExtraRate(50);
    }
}
