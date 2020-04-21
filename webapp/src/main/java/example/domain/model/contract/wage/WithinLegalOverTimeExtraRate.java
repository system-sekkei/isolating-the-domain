package example.domain.model.contract.wage;

import example.domain.model.legislation.ExtraPayRate;

/**
 * 法定時間内残業 時間外割増率
 */
public class WithinLegalOverTimeExtraRate {
    ExtraPayRate value;

    public WithinLegalOverTimeExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static WithinLegalOverTimeExtraRate regulation() {
        return new WithinLegalOverTimeExtraRate(0);
    }
}
