package example.domain.model.contract;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 契約開始日
 */
public class ContractEffectiveDate {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    @Deprecated
    public ContractEffectiveDate() {
    }

    public ContractEffectiveDate(String value) {
        this(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
    }

    public ContractEffectiveDate(LocalDate value) {
        this.value = value;
    }

    // TODO: 契約期間がない契約はありえない
    public static ContractEffectiveDate none() {
        return new ContractEffectiveDate(distantFuture());
    }

    public static LocalDate distantFuture() {
        return LocalDate.of(9999, 12, 31);
    }

    @Override
    public String toString() {
        if (notAvailable()) {
            return "未設定";
        }
        return value.format(DateTimeFormatter.ofPattern("uuuu年M月d日"));
    }

    private boolean notAvailable() {
        return value.equals(distantFuture());
    }

    public boolean isAfter(LocalDate date) {
        return value.isAfter(date);
    }

    public LocalDate value() {
        return value;
    }
}
