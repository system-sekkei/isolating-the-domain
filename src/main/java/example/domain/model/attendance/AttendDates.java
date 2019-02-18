package example.domain.model.attendance;

import example.domain.model.timerecord.WorkDate;
import example.domain.type.date.Dates;

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

    public Dates toDates() {
        return new Dates(list.stream().map(WorkDate::toDate).collect(toList()));
    }
}
