package example.api.view.attendance;

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

public class AttendanceListView {
    @JsonIgnore
    Employee employee;
    @JsonIgnore
    Attendance attendance;

    public AttendanceListView(Employee employee, Attendance attendance) {
        this.employee = employee;
        this.attendance = attendance;
    }

    @JsonProperty("employeeName")
    String employeeName() {
        return employee.name().toString();
    }

    @JsonProperty("totalWorkTime")
    String totalWorkTime() {
        return attendance.totalWorkTime().toString();
    }

    @JsonProperty("list")
    List<Map<String, String>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        for (WorkDate workDate : attendance.listWorkDates()) {
            Map<String, String> data = new HashMap<>();
            AttendanceStatus attendanceStatus = attendance.statusOf(workDate);
            data.put("workDate", workDate.toString());
            if (attendanceStatus.isWork()) {
                data.put("startTimePoint", attendance.at(workDate).actualWorkTime().workRange().start().toString());
                data.put("endTimePoint", attendance.at(workDate).actualWorkTime().workRange().end().toString());
                data.put("breakTime", attendance.at(workDate).actualWorkTime().breakTime().toString());
                data.put("workTime", attendance.at(workDate).actualWorkTime().workTime().toString());
            }
            list.add(data);
        }
        return list;
    }
}
