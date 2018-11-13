package example.domain.model.labour_standards_law;

/**
 * 割増賃金
 */
public enum ExtraPay {
    時間外(new ExtraPayRate("0.25")),
    休日(new ExtraPayRate("0.35")),
    深夜(new ExtraPayRate("0.25"));

    ExtraPayRate leastExtraPayRate;

    ExtraPay(ExtraPayRate leastExtraPayRate) {
        this.leastExtraPayRate = leastExtraPayRate;
    }

    public ExtraPayRate leastExtraPayRate() {
        return leastExtraPayRate;
    }
}

