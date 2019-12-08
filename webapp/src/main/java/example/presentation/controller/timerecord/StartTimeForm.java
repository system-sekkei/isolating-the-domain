package example.presentation.controller.timerecord;

import example.domain.FormatCheck2;
import example.domain.model.timerecord.evaluation.WorkDate;
import example.domain.model.timerecord.timefact.StartDateTime;
import example.domain.type.datetime.DateTime;
import example.domain.type.time.Time;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;

public class StartTimeForm {
    @Valid
    StartHour hour;

    @Valid
    StartMinute minute;

    public StartTimeForm() {
    }

    public StartTimeForm(StartHour hour, StartMinute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public StartDateTime startDateTime(WorkDate workDate) {
        return new StartDateTime(DateTime.parse(workDate.toString(), hour.toString(), minute.toString()));
    }

    boolean valid;

    @AssertTrue(message = "開始時刻が不正です", groups = FormatCheck2.class)
    public boolean isValid() {
        try {
            new Time(hour.toInt(), minute.toInt());
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }

        return true;
    }
}
