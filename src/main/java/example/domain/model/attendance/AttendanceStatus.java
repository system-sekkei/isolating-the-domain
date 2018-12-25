package example.domain.model.attendance;

/**
 * 出勤状況
 */
public enum AttendanceStatus {
    出勤,
    非出勤;

    public boolean isWork() {
        return this == 出勤;
    }
}
