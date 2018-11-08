package example.domain.type.time;

/**
 * 時間(数)
 */
public class Hour {
    int value;

    Hour(int time) {
        value = time;
    }

    Minute toMinute() {
        return new Minute(value * 60);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
