package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 法定外休日 時間外割増率
 */
public class NonLegalDaysOffExtraRate {
    ExtraPayRate value;

    public NonLegalDaysOffExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static NonLegalDaysOffExtraRate regulation() {
        return new NonLegalDaysOffExtraRate(35);
    }

    public ExtraPayRate value() {
        return value;
    }
}
