package example.domain.type.amount;

/**
 * 端数処理
 */
public enum RoundingMode {
    切り上げ(java.math.RoundingMode.UP),
    切り捨て(java.math.RoundingMode.DOWN),
    四捨五入(java.math.RoundingMode.HALF_UP);

    java.math.RoundingMode value;

    RoundingMode(java.math.RoundingMode value) {
        this.value = value;
    }
}
