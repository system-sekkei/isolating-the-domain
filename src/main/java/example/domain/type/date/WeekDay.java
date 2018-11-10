package example.domain.type.date;

public enum WeekDay {
    平日,
    週末;

    static WeekDay of(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.土 || dayOfWeek == DayOfWeek.日) {
            return 週末;
        }
        return 平日;
    }
}
