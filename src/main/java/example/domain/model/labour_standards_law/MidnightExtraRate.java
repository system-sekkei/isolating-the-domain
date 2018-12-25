package example.domain.model.labour_standards_law;

/**
 * 深夜割増率
 */
public class MidnightExtraRate {
    ExtraPayRate value;

    public MidnightExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static MidnightExtraRate legal() {
        return new MidnightExtraRate(35);
    }

    public ExtraPayRate value() {
        return value;
    }
}
