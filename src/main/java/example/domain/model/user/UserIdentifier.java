package example.domain.model.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserIdentifier {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスが正しくありません。")
    String value = "";

    public UserIdentifier() {
    }

    public UserIdentifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
