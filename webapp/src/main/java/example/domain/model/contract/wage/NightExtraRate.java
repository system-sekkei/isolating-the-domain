package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 深夜割増率
 */
public class NightExtraRate {
    ExtraPayRate value;

    @Deprecated
    public NightExtraRate() {
    }

    public NightExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static NightExtraRate legal() {
        return new NightExtraRate(35);
    }

    public ExtraPayRate value() {
        return value;
    }
}
