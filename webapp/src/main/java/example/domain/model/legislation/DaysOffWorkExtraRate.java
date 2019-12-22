package example.domain.model.legislation;

/**
 * 休日労働割増率
 */
public class DaysOffWorkExtraRate {
    ExtraPayRate value;

    @Deprecated
    public DaysOffWorkExtraRate() {
    }

    public DaysOffWorkExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static DaysOffWorkExtraRate legal() {
        return new DaysOffWorkExtraRate(35);
    }

    public ExtraPayRate value() {
        return value;
    }
}
