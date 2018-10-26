package example.domain.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * メールアドレス
 */
public class MailAddress {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスが正しくありません。")
    String value = "";

    @Override
    public String toString() {
        return value;
    }
}
