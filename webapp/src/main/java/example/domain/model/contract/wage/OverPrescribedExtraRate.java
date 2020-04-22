package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 所定超 時間外割増率
 */
public class OverPrescribedExtraRate {
    ExtraPayRate value;

    public OverPrescribedExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverPrescribedExtraRate regulation() {
        return new OverPrescribedExtraRate(0);
    }
}
