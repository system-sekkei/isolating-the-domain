package example.presentation.controller.timerecord;

import example.domain.validation.Required;

import javax.validation.constraints.NotBlank;

public class EndMinute {
    @NotBlank(message = "終了時刻を入力してください", groups = Required.class)
    String value;

    public EndMinute() {
    }

    public EndMinute(String value) {
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
