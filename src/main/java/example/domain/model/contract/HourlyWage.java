package example.domain.model.contract;

import example.domain.model.labour_standards_law.ExtraPayRate;

/**
 * 時給
 */
public class HourlyWage {
    Integer value;

    public HourlyWage(Integer value) {
        this.value = value;
    }

    public HourlyWage(String value) {
        this.value = Integer.parseInt(value);
    }

    public Integer value() {
        return value;
    }

    public String toString() {
        return value.toString();
    }

    public HourlyWage withExtraRate(ExtraPayRate extraRate) {
        // TODO 計算
        return this;
    }
}
