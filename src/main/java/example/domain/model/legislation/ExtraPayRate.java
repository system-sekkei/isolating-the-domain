package example.domain.model.legislation;

import example.domain.type.amount.Percentage;

/**
 * 割増率（％）
 */
public class ExtraPayRate {
    Percentage value;

    public ExtraPayRate(Integer value) {
        this.value = new Percentage(value);
    }

    public Percentage value() {
        return value;
    }
}
