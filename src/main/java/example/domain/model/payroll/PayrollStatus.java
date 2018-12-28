package example.domain.model.payroll;

/**
 * 給与ステータス
 */
public enum PayrollStatus {
    有効("payroll.normal"),
    時給登録無し("payroll.payment-amount-unregistered"),
    稼働登録無し("payroll.not-working");

    String messageKey;

    PayrollStatus(String messageKey) {
        this.messageKey = messageKey;
    }

    public String messageKey() {
        return messageKey;
    }

    public boolean available() {
        return this == 有効;
    }
}
