package example.domain.model.timerecord.evaluation;


/**
 * 勤務終了時刻 検証エラー
 */
public enum EndTimeValidResult {
    正常(""),
    翌日の勤務時刻と重複("翌日の勤務時刻と重複しています");

    String message;

    EndTimeValidResult(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    public boolean hasError() {
        return this != 正常;
    }
}
