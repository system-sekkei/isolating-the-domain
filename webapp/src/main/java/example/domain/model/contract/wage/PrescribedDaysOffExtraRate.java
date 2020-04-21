package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 所定休日労働 時間外割増率
 */
public class PrescribedDaysOffExtraRate {
    ExtraPayRate value;

    public PrescribedDaysOffExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static PrescribedDaysOffExtraRate regulation() {
        return new PrescribedDaysOffExtraRate(35);
    }
}
