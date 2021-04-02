package example.presentation.controller.timerecord;

import example.domain.validation.Required;

import javax.validation.constraints.NotBlank;

public class StartMinute {
    @NotBlank(message = "開始時刻(分)を入力してください", groups = Required.class)
    String value;

    public StartMinute() {
    }

    public StartMinute(String value) {
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
