package example.domain.model.attendance;

import example.domain.type.time.Minute;

/**
 * 時間外労働時間
 */
public class OverWorkTime {
    private final Minute value;

    public OverWorkTime(Minute value) {
        this.value = value;
    }

    public Minute minute() {
        return value;
    }
}
