package example.domain.model.legislation;

import example.domain.type.time.Minute;

/**
 * 法定休日労働
 */
public class StatutoryDaysOffWork {
    Minute value;

    public StatutoryDaysOffWork(Minute value) {
        this.value = value;
    }
}
