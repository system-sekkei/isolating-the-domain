package example.model.user;

import example.model.user.validation.OnRegisterWithPhoneNumber;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class PhoneNumber {
    @NotBlank(message = "電話番号を入力してください", groups = OnRegisterWithPhoneNumber.class)
    @Pattern(regexp = "[0-9()-]*", message = "数字、ハイフン、括弧で入力してください")
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
