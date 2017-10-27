package example.domain.model.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class Name {

    @NotBlank(message = "名前を入力してください。")
    @Size(max = 40, message = "40文字以内で入力してください。")
    String value = "";

    @Override
    public String toString() {
        return value;
    }
}
