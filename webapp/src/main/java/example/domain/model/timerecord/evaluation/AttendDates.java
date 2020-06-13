package example.domain.model.timerecord.evaluation;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 実勤務日付一覧
 */
public class AttendDates {
    List<WorkDate> list;

    public AttendDates(List<WorkDate> list) {
        this.list = list;
    }

    public List<LocalDate> toDates() {
        return list.stream().map(WorkDate::toDate).collect(toList());
    }
}
