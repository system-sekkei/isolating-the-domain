package example.domain.model.user;

import org.hibernate.validator.constraints.NotBlank;

public class Name {

    @NotBlank(message = "名前を入力してください。")
    String value = "";

    @Override
    public String toString() {
        return value;
    }
}
