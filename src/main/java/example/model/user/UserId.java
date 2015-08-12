package example.model.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by haljik on 15/06/04.
 */
public class UserId {
    @NotBlank(message = "ユーザーIDを入力してください。")
    @Email(message = "ユーザーIDはメールアドレスを入力してください。")
    String value;

    public UserId(String value) {
        this.value = value;
    }

    //For MyBatis
    public UserId() {
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
