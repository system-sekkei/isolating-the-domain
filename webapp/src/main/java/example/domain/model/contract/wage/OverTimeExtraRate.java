package example.domain.model.contract.wage;

/**
 * 時間外及び休日、深夜の割増賃金
 */
public class OverTimeExtraRate {
    OverLegalUpTo60HoursExtraRate overLegalUpTo60HoursExtraRate;
    OverLegalMoreThan60HoursExtraRate overLegalMoreThan60HoursExtraRate;
    OverPrescribedExtraRate overPrescribedExtraRate;
    LegalDaysOffExtraRate legalDaysOffExtraRate;
    NonLegalDaysOffExtraRate nonLegalDaysOffExtraRate;
    NightExtraRate nightExtraRate;

    @Deprecated
    public OverTimeExtraRate() {
    }

    public OverTimeExtraRate(OverLegalUpTo60HoursExtraRate overLegalUpTo60HoursExtraRate, OverLegalMoreThan60HoursExtraRate overLegalMoreThan60HoursExtraRate,
                             OverPrescribedExtraRate overPrescribedExtraRate, LegalDaysOffExtraRate legalDaysOffExtraRate, NonLegalDaysOffExtraRate nonLegalDaysOffExtraRate, NightExtraRate nightExtraRate) {
        this.overLegalUpTo60HoursExtraRate = overLegalUpTo60HoursExtraRate;
        this.overLegalMoreThan60HoursExtraRate = overLegalMoreThan60HoursExtraRate;
        this.overPrescribedExtraRate = overPrescribedExtraRate;
        this.legalDaysOffExtraRate = legalDaysOffExtraRate;
        this.nonLegalDaysOffExtraRate = nonLegalDaysOffExtraRate;
        this.nightExtraRate = nightExtraRate;
    }

    public static OverTimeExtraRate regulation() {
        return new OverTimeExtraRate(OverLegalUpTo60HoursExtraRate.regulation(), OverLegalMoreThan60HoursExtraRate.regulation(),
                OverPrescribedExtraRate.regulation(), LegalDaysOffExtraRate.regulation(), NonLegalDaysOffExtraRate.regulation(), NightExtraRate.regulation());
    }

    public OverLegalUpTo60HoursExtraRate overLegalTimeExtraRate() {
        return overLegalUpTo60HoursExtraRate;
    }

    public NightExtraRate nightExtraRate() {
        return nightExtraRate;
    }

    public OverLegalUpTo60HoursExtraRate overLegalUpTo60HoursExtraRate() {
        return overLegalUpTo60HoursExtraRate;
    }

    public OverLegalMoreThan60HoursExtraRate overLegalMoreThan60HoursExtraRate() {
        return overLegalMoreThan60HoursExtraRate;
    }

    public OverPrescribedExtraRate overPrescribedExtraRate() {
        return overPrescribedExtraRate;
    }

    public LegalDaysOffExtraRate legalDaysOffExtraRate() {
        return legalDaysOffExtraRate;
    }

    public NonLegalDaysOffExtraRate nonLegalDaysOffExtraRate() {
        return nonLegalDaysOffExtraRate;
    }
}
