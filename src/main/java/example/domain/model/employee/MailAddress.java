package example.domain.model.employee;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * メールアドレス
 */
public class MailAddress {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスが正しくありません。")
    String value = "";

    public MailAddress() {
    }

    public MailAddress(String mailAddress) {
        value = mailAddress;
    }

    @Override
    public String toString() {
        return value;
    }
}
