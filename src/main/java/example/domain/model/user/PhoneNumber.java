package example.domain.model.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PhoneNumber {

    @NotBlank(message = "電話番号を入力してください")
    @Pattern(regexp = "([0-9]{2,4}-[0-9]{2,4}-[0-9]{2,4})?", message = "xx-xxxx-xxxxの形式で入力してください")
    String text;

    public PhoneNumber(@NotNull String text) {
        this.text = text;
    }

    public PhoneNumber() {
        text = "";
    }


    @Override
    public String toString() {
        return text;
    }
}
