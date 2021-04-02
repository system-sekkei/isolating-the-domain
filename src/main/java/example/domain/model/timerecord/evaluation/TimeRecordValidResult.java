package example.domain.model.timerecord.evaluation;

public class TimeRecordValidResult {
    StartTimeValidResult startTimeValidResult;
    EndTimeValidResult endTimeValidResult;

    public TimeRecordValidResult(StartTimeValidResult startTimeValidResult, EndTimeValidResult endTimeValidResult) {
        this.startTimeValidResult = startTimeValidResult;
        this.endTimeValidResult = endTimeValidResult;
    }

    public StartTimeValidResult startTimeValidResult() {
        return startTimeValidResult;
    }

    public EndTimeValidResult endTimeValidResult() {
        return endTimeValidResult;
    }
}
