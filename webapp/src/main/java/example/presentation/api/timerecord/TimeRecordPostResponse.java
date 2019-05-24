package example.presentation.api.timerecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

/**
 * 勤務時間の登録レスポンス
 */
class TimeRecordPostResponse {
    @JsonIgnore
    TimeRecord timeRecord;
    @JsonIgnore
    BindingResult bindingResult;

    private TimeRecordPostResponse(TimeRecord timeRecord, BindingResult bindingResult) {
        this.timeRecord = timeRecord;
        this.bindingResult = bindingResult;
    }

    static TimeRecordPostResponse ok(TimeRecord timeRecord, BindingResult result) {
        return new TimeRecordPostResponse(timeRecord, result);
    }

    static TimeRecordPostResponse ng(BindingResult result) {
        return new TimeRecordPostResponse(null, result);
    }

    @JsonProperty("status")
    String status() {
        if (bindingResult.hasErrors()) return PostedResult.NG.toString();
        return PostedResult.OK.toString();
    }

    @JsonProperty("errors")
    List<String> errors() {
        if (!bindingResult.hasErrors()) return Collections.emptyList();
        List<String> list = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            list.add(fieldError.getDefaultMessage());
        }
        return list;
    }

    @JsonProperty("succeeded")
    Map<String, String> succeededData() {
        if (bindingResult.hasErrors()) return null;
        Map<String, String> data = new HashMap<>();
        data.put("employeeNumber", timeRecord.employeeNumber().toString());
        data.put("workDate", timeRecord.workDate().toString());
        return data;
    }
}
