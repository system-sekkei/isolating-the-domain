package example.domain.model.attendance;

/**
 * 勤怠状況
 */
public enum AttendanceStatus {
    出勤,
    非出勤;

    public static AttendanceStatus from(Recorded recorded) {
        if (recorded == Recorded.記録あり) return 出勤;
        return 非出勤;
    }

    public boolean isWork() {
        return this == 出勤;
    }
}
