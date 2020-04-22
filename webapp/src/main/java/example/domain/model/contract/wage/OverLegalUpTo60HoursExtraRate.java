package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 所定時間外 法定超 月60時間以内 時間外割増率
 */
public class OverLegalUpTo60HoursExtraRate {
    ExtraPayRate value;

    @Deprecated
    public OverLegalUpTo60HoursExtraRate() {
    }

    public OverLegalUpTo60HoursExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverLegalUpTo60HoursExtraRate regulation() {
        return new OverLegalUpTo60HoursExtraRate(25);
    }

    public ExtraPayRate value() {
        return value;
    }
}
