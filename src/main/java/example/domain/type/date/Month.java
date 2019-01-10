package example.domain.type.date;

import java.util.stream.Stream;

/**
 * 月
 */
public enum Month {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    Integer month;

    private Month(int month) {
        this.month = month;
    }

    public Integer value() {
        return month;
    }

    public String toString() {
        return String.format("%2d", value());
    }

    public static Month of(Integer month) {
        return Stream.of(values()).filter(m -> m.month.equals(month)).findFirst().orElseThrow(() -> new IllegalArgumentException());
    }

    public static Month of(String month) {
        return of(Integer.parseInt(month));
    }
}
