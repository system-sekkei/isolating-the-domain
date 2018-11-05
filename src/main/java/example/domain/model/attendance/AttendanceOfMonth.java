package example.domain.model.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 月次勤怠
 */
public class AttendanceOfMonth {
    List<AttendanceOfDay> list;
    public AttendanceOfMonth(){}
    public AttendanceOfMonth(Stream<AttendanceOfDay> workTimes) {
        list = workTimes.collect(Collectors.toList());
    }
    public AttendanceOfMonth(List<AttendanceOfDay> workTimes) {
        list = new ArrayList<>(workTimes);
    }
}
