package example.domain.model.wage;

/**
 * 所定超 時給割増額
 */
public class OverPrescribedHourlyExtraWage {
    HourlyWage value;

    public OverPrescribedHourlyExtraWage(HourlyWage value) {
        this.value = value;
    }
}
