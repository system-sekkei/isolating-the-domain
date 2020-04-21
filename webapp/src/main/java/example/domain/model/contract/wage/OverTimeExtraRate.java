package example.domain.model.contract.wage;

/**
 * 時間外及び休日、深夜の割増賃金
 */
public class OverTimeExtraRate {
    WithinLegalOverTimeExtraRate withinLegalOverTimeExtraRate;
    OverLegalTimeExtraRate overLegalTimeExtraRate;
    PrescribedDaysOffExtraRate prescribedDaysOffExtraRate;
    LegalDaysOffExtraRate legalDaysOffExtraRate;
    OverTime60HoursPerMonthExtraRate overTime60HoursPerMonthExtraRate;
    NightExtraRate nightExtraRate;

    @Deprecated
    public OverTimeExtraRate() {
    }

    public OverTimeExtraRate(
        WithinLegalOverTimeExtraRate withinLegalOverTimeExtraRate,
        OverLegalTimeExtraRate overLegalTimeExtraRate,
        PrescribedDaysOffExtraRate prescribedDaysOffExtraRate,
        LegalDaysOffExtraRate legalDaysOffExtraRate,
        OverTime60HoursPerMonthExtraRate overTime60HoursPerMonthExtraRate,
        NightExtraRate nightExtraRate) {

        this.withinLegalOverTimeExtraRate = withinLegalOverTimeExtraRate;
        this.overLegalTimeExtraRate = overLegalTimeExtraRate;
        this.prescribedDaysOffExtraRate = prescribedDaysOffExtraRate;
        this.legalDaysOffExtraRate = legalDaysOffExtraRate;
        this.overTime60HoursPerMonthExtraRate = overTime60HoursPerMonthExtraRate;
        this.nightExtraRate = nightExtraRate;
    }

    public OverLegalTimeExtraRate overLegalTimeExtraRate() {
        return overLegalTimeExtraRate;
    }

    public NightExtraRate nightExtraRate() {
        return nightExtraRate;
    }
}
