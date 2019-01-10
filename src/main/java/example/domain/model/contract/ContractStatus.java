package example.domain.model.contract;

/**
 * 契約状態
 */
public enum ContractStatus {
    契約あり,
    契約なし;

    public boolean disable() {
        return this == 契約なし;
    }
}
