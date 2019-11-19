package example.application.coordinator.timerecord;


/**
 * 勤務実績 検証エラー
 */
public enum TimeRecordValidError {
    前日の勤務時刻と重複("overlapWithPreviousWorkRange", "前日の勤務時刻と重複しています"),
    翌日の勤務時刻と重複("overlapWithNextWorkRange", "翌日の勤務時刻と重複しています");

    String field;
    String message;

    TimeRecordValidError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String field() {
        return field;
    }

    public String message() {
        return message;
    }
}
