package example.model.user;

import example.model.user.validation.OnRegister;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by haljik on 15/06/04.
 */
public class UserId {
    @NotBlank(message = "ユーザーIDを入力してください。", groups = OnRegister.class)
    @Email(message = "ユーザーIDはメールアドレスを入力してください。", groups = OnRegister.class)
    String value;

    public UserId(@NotNull String value) {
        this.value = value;
    }

    public UserId() {
        this.value = "";
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
