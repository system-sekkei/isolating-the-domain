package example.domain.model.legislation;

import example.domain.type.time.Minute;

/**
 * 休日労働
 */
public class DaysOffWork {
    Minute value;

    public DaysOffWork(Minute value) {
        this.value = value;
    }
}
