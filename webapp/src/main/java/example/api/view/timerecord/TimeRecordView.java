package example.api.view.timerecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.timerecord.TimeRecord;

import java.util.HashMap;
import java.util.Map;

public class TimeRecordView {
    @JsonIgnore
    TimeRecord timeRecord;

    public TimeRecordView(TimeRecord timeRecord) {
        this.timeRecord = timeRecord;
    }

    @JsonProperty("posted")
    Map<String, String> posted() {
        Map<String, String> data = new HashMap<>();
        data.put("employeeNumber", timeRecord.employeeNumber().toString());
        data.put("workDate", timeRecord.workDate().toString());
        return data;
    }
}
