package example.domain.model.contract.wage;

/**
 * 時間外及び休日、深夜の割増賃金
 */
public class OverTimeExtraRate {
    OverLegalWithin60HoursExtraRate overLegalWithin60HoursExtraRate;
    OverLegalMoreThan60HoursExtraRate overLegalMoreThan60HoursExtraRate;
    LegalDaysOffExtraRate legalDaysOffExtraRate;
    NightExtraRate nightExtraRate;

    @Deprecated
    public OverTimeExtraRate() {
    }

    public OverTimeExtraRate(OverLegalWithin60HoursExtraRate overLegalWithin60HoursExtraRate, OverLegalMoreThan60HoursExtraRate overLegalMoreThan60HoursExtraRate, LegalDaysOffExtraRate legalDaysOffExtraRate, NightExtraRate nightExtraRate) {
        this.overLegalWithin60HoursExtraRate = overLegalWithin60HoursExtraRate;
        this.overLegalMoreThan60HoursExtraRate = overLegalMoreThan60HoursExtraRate;
        this.legalDaysOffExtraRate = legalDaysOffExtraRate;
        this.nightExtraRate = nightExtraRate;
    }

    public static OverTimeExtraRate regulation() {
        return new OverTimeExtraRate(OverLegalWithin60HoursExtraRate.regulation(), OverLegalMoreThan60HoursExtraRate.regulation(),
                LegalDaysOffExtraRate.regulation(), NightExtraRate.regulation());
    }

    public OverLegalWithin60HoursExtraRate overLegalTimeExtraRate() {
        return overLegalWithin60HoursExtraRate;
    }

    public NightExtraRate nightExtraRate() {
        return nightExtraRate;
    }

    public OverLegalWithin60HoursExtraRate overLegalUpTo60HoursExtraRate() {
        return overLegalWithin60HoursExtraRate;
    }

    public OverLegalMoreThan60HoursExtraRate overLegalMoreThan60HoursExtraRate() {
        return overLegalMoreThan60HoursExtraRate;
    }

    public LegalDaysOffExtraRate legalDaysOffExtraRate() {
        return legalDaysOffExtraRate;
    }
}
