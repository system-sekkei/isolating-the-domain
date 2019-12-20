package example.domain.model.legislation;

/**
 * 法定休日労働割増率
 */
public class StatutoryDaysOffWorkExtraRate {
    ExtraPayRate value;

    @Deprecated
    public StatutoryDaysOffWorkExtraRate() {
    }

    public StatutoryDaysOffWorkExtraRate(Integer value) {
        this.value = new ExtraPayRate(value);
    }

    public static StatutoryDaysOffWorkExtraRate legal() {
        return new StatutoryDaysOffWorkExtraRate(35);
    }

    public ExtraPayRate value() {
        return value;
    }
}
