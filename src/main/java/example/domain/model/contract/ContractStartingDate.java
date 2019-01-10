package example.domain.model.contract;

import example.domain.type.date.Date;

import java.time.format.DateTimeFormatter;

/**
 * 契約開始日
 */
public class ContractStartingDate {

    Date value;

    public ContractStartingDate(String value) {
        this(new Date(value));
    }

    public ContractStartingDate(Date value) {
        this.value = value;
    }

    public static ContractStartingDate none() {
        return new ContractStartingDate(Date.distantFuture());
    }

    @Override
    public String toString() {
        if (notAvailable()) {
            return "未設定";
        }
        return value.value().format(DateTimeFormatter.ofPattern("uuuu年M月d日"));
    }

    private boolean notAvailable() {
        return value.hasSameValue(Date.distantFuture());
    }

    public boolean isAfter(Date date) {
        return value.isAfter(date);
    }

    public Date value() {
        return value;
    }
}
