package example.domain.model.attendance;

/**
 * 出勤区分
 */
public enum Attendance {
    出勤,
    休み;

    public boolean work() {
        return this == 出勤;
    }
}
