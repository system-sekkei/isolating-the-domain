package example.domain.type.time;

/**
 * 時間(数)
 */
public class Hour {
    int value;

    public Hour(int time) {
        value = time;
    }

    public Minute toMinute() {
        return new Minute(value * 60);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
