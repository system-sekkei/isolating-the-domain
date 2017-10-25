package example.domain.model.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

public class UserEmail {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスが正しくありません。")
    String value = "";

    public UserEmail() {
    }

    public UserEmail(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
