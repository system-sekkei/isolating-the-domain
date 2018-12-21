package example.domain.model.attendance;

import example.domain.type.time.Minute;

/**
 * 深夜労働時間
 */
public class MidnightWorkTime {
    private final Minute value;

    public MidnightWorkTime(Minute value) {
        this.value = value;
    }

    public Minute minute() {
        return value;
    }
}
