package example.domain.model.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by haljik on 15/06/04.
 */
public class UserIdentifier {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスが正しくありません。")
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
