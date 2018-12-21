package example.domain.model.contract;

import example.domain.type.date.Date;

/**
 * 契約開始日
 */
public class ContractStartingDate {

    Date value;

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
        return value.toString();
    }

    private boolean notAvailable() {
        return value.hasSameValue(Date.distantFuture());
    }
}
