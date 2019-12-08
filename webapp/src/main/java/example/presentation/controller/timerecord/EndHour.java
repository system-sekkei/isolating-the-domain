package example.presentation.controller.timerecord;

import example.domain.FormatCheck;

import javax.validation.constraints.NotBlank;

public class EndHour {
    @NotBlank(message = "終了時刻を入力してください", groups = FormatCheck.class)
    String value;

    public EndHour() {
    }

    public EndHour(String value) {
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
