package example.domain.model.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 勤怠一覧
 */
public class WorkTimes {
    List<WorkTime> list;
    public WorkTimes(){}
    public WorkTimes(Stream<WorkTime> workTimes) {
        list = workTimes.collect(Collectors.toList());
    }
    public WorkTimes(List<WorkTime> workTimes) {
        list = new ArrayList<>(workTimes);
    }
}
