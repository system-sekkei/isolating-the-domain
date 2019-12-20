package example.domain.model.legislation;

/**
 * 時間外割増率
 */
public class OverTimeMaxHoursExtraRate {
    ExtraPayRate value;

    public OverTimeMaxHoursExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static OverTimeMaxHoursExtraRate legal() {
        return new OverTimeMaxHoursExtraRate(50);
    }
}
