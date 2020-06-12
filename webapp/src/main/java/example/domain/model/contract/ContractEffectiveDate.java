package example.domain.model.contract;

import example.domain.type.date.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 契約開始日
 */
public class ContractEffectiveDate {

    Date value;

    @Deprecated
    public ContractEffectiveDate() {
    }

    public ContractEffectiveDate(String value) {
        this(new Date(LocalDate.parse(value, DateTimeFormatter.ISO_DATE)));
    }

    public ContractEffectiveDate(Date value) {
        this.value = value;
    }

    // TODO: 契約期間がない契約はありえない
    public static ContractEffectiveDate none() {
        return new ContractEffectiveDate(distantFuture());
    }

    public static Date distantFuture() {
        return new Date(LocalDate.of(9999, 12, 31));
    }

    @Override
    public String toString() {
        if (notAvailable()) {
            return "未設定";
        }
        return value.value.format(DateTimeFormatter.ofPattern("uuuu年M月d日"));
    }

    private boolean notAvailable() {
        return value.value.equals(distantFuture().value);
    }

    public boolean isAfter(Date date) {
        return value.value.isAfter(date.value);
    }

    public Date value() {
        return value;
    }
}
