package example.domain.model.payroll;

import example.domain.model.contract.ContractStatus;

/**
 * 給与ステータス
 */
public enum PayrollStatus {
    有効("payroll.normal", ContractStatus.契約あり),
    時給登録無し("payroll.payment-amount-unregistered", ContractStatus.契約なし),
    稼働登録無し("payroll.not-working", ContractStatus.判定不能);

    String messageKey;
    ContractStatus contractStatus;

    PayrollStatus(String messageKey, ContractStatus contractStatus) {
        this.messageKey = messageKey;
        this.contractStatus = contractStatus;
    }

    public static PayrollStatus from(ContractStatus contractStatus) {
        for (PayrollStatus value : values()) {
            if (value.contractStatus == contractStatus) {
                return value;
            }
        }
        throw new IllegalStateException(contractStatus.toString());
    }

    public String messageKey() {
        return messageKey;
    }

    public boolean available() {
        return this == 有効;
    }
}
