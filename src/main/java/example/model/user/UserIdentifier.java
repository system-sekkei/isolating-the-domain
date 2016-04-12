package example.model.user;

import example.model.user.validation.OnRegister;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by haljik on 15/06/04.
 */
public class UserIdentifier {
    @NotBlank(message = "メールアドレスを入力してください。", groups = OnRegister.class)
    @Email(message = "メールアドレスが正しくありません。", groups = OnRegister.class)
    String mail;

    public UserIdentifier(@NotNull String mail) {
        this.mail = mail;
    }

    public UserIdentifier() {
        this.mail = "";
    }

    public String mail() { return mail;}

    @Override
    public String toString() {
        return mail;
    }
}
