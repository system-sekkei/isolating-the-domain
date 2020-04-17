package example.domain.model.contract;

import example.domain.model.legislation.ExtraPayRate;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.NightHourlyExtraWage;
import example.domain.model.wage.OverTimeHourlyExtraWage;
import example.domain.type.amount.Amount;
import example.domain.type.amount.RoundingMode;

import java.math.BigDecimal;

/**
 * 基本の時給
 */
public class BaseHourlyWage {

    HourlyWage value;

    @Deprecated
    public BaseHourlyWage() {
    }

    public BaseHourlyWage(Integer value) {
        this(new HourlyWage(value));
    }

    public BaseHourlyWage(String value) {
        this(new HourlyWage(new Amount(value)));
    }

    BaseHourlyWage(HourlyWage value) {
        this.value = value;
    }

    public static BaseHourlyWage disable() {
        return new BaseHourlyWage(HourlyWage.disable());
    }

    public BigDecimal toBigDecimal() {
        return value.value().value();
    }

    public HourlyWage value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage(OverLegalTimeExtraRate overLegalTimeExtraRate) {
        return new OverTimeHourlyExtraWage(withExtraPayRate(overLegalTimeExtraRate.value()));
    }

    public NightHourlyExtraWage nightHourlyExtraWage(NightExtraRate nightExtraRate) {
        return new NightHourlyExtraWage(withExtraPayRate(nightExtraRate.value()));
    }

    HourlyWage withExtraPayRate(ExtraPayRate extraPayRate) {
        return new HourlyWage(value.value().multiply(extraPayRate.value(), RoundingMode.切り捨て));
    }
}
