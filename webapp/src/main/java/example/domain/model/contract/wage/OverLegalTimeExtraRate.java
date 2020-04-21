package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 法定時間外残業 時間外割増率
 */
public class OverLegalTimeExtraRate {
    ExtraPayRate value;

    @Deprecated
    public OverLegalTimeExtraRate() {
    }

    public OverLegalTimeExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverLegalTimeExtraRate regulation() {
        return new OverLegalTimeExtraRate(25);
    }

    public ExtraPayRate value() {
        return value;
    }
}
