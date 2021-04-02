package example.domain.model.timerecord.evaluation;


/**
 * 勤務開始時刻 検証エラー
 */
public enum StartTimeValidResult {
    正常(""),
    前日の勤務時刻と重複( "前日の勤務時刻と重複しています");

    String message;

    StartTimeValidResult(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    public boolean hasError() {
        return this != 正常;
    }
}
