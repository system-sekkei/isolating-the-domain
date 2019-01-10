package example.domain.model.legislation;

/**
 * 時間外割増率
 */
public class OverTimeExtraRate {
    ExtraPayRate value;

    public OverTimeExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverTimeExtraRate legal() {
        return new OverTimeExtraRate(25);
    }

    public ExtraPayRate value() {
        return value;
    }
}
