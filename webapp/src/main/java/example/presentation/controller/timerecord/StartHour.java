package example.presentation.controller.timerecord;

import example.domain.FormatCheck;

import javax.validation.constraints.NotBlank;

public class StartHour {
    @NotBlank(message = "開始時刻を入力してください", groups = FormatCheck.class)
    String value;

    public StartHour() {
    }

    public StartHour(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    int toInt() {
        return Integer.parseInt(value);
    }
}
