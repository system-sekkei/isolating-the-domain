package example.domain.type.date;

/**
 * 曜日
 */
public enum DayOfWeek {
    月(java.time.DayOfWeek.MONDAY),
    火(java.time.DayOfWeek.TUESDAY),
    水(java.time.DayOfWeek.WEDNESDAY),
    木(java.time.DayOfWeek.THURSDAY),
    金(java.time.DayOfWeek.FRIDAY),
    土(java.time.DayOfWeek.SATURDAY),
    日(java.time.DayOfWeek.SUNDAY);

    private final java.time.DayOfWeek dayOfWeek;

    DayOfWeek(java.time.DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static DayOfWeek of(java.time.DayOfWeek dayOfWeek) {
        for (DayOfWeek value : values()) {
            if (value.dayOfWeek == dayOfWeek) {
                return value;
            }
        }
        throw new IllegalArgumentException(dayOfWeek.toString());
    }
}
