package example.api.view.timerecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.timerecord.TimeRecord;

public class TimeRecordView {
    @JsonIgnore
    TimeRecord timeRecord;

    public TimeRecordView(TimeRecord timeRecord) {
        this.timeRecord = timeRecord;
    }

    @JsonProperty("employeeNumber")
    String employeeNumber() {
        return timeRecord.employeeNumber().toString();
    }

    @JsonProperty("workDate")
    String workDate() {
        return timeRecord.workDate().toString();
    }
}
