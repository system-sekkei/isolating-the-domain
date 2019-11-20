package example.domain.model.timerecord.evaluation;

import java.util.List;

/**
 * 勤務実績 検証結果
 */
public class TimeRecordValidResult {
    List<TimeRecordValidError> errors;

    public TimeRecordValidResult(List<TimeRecordValidError> errors) {
        this.errors = errors;
    }

    public List<TimeRecordValidError> errors() {
        return errors;
    }
}
