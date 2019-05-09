package example.presentation.api.attendance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.WorkDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 勤務時間一覧の取得レスポンス
 */
class AttendanceGetResponse {
    @JsonIgnore
    Employee employee;
    @JsonIgnore
    Attendance attendance;

    AttendanceGetResponse(Employee employee, Attendance attendance) {
        this.employee = employee;
        this.attendance = attendance;
    }

    @JsonProperty("氏名")
    String employeeName() {
        return employee.name().toString();
    }

    @JsonProperty("前月")
    String previousMonth() {
        return attendance.month().before().toString();
    }

    @JsonProperty("翌月")
    String nextMonth() {
        return attendance.month().after().toString();
    }

    @JsonProperty("総勤務時間")
    String totalWorkTime() {
        return attendance.totalWorkTime().toString();
    }

    @JsonProperty("list")
    List<Map<String, String>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        for (WorkDate workDate : attendance.listWorkDates()) {
            Map<String, String> data = new HashMap<>();
            AttendanceStatus attendanceStatus = attendance.statusOf(workDate);
            data.put("日付", String.valueOf(workDate.dayOfMonth()));
            data.put("曜日", workDate.dayOfWeek().toString());
            if (attendanceStatus.isWork()) {
                data.put("開始時刻", attendance.at(workDate).actualWorkTime().timeRange().start().toString());
                data.put("終了時刻", attendance.at(workDate).actualWorkTime().timeRange().end().toString());
                data.put("休憩時間", attendance.at(workDate).actualWorkTime().breakTime().toString());
                data.put("勤務時間", attendance.at(workDate).actualWorkTime().workTime().toString());
            }
            list.add(data);
        }
        return list;
    }
}
