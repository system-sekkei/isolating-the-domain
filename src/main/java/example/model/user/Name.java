package example.model.user;

import example.model.user.validation.OnRegister;
import example.model.user.validation.OnUpdate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by haljik on 15/06/04.
 */
public class Name {
    @NotEmpty(message = "名前を入力してください。", groups = {OnRegister.class, OnUpdate.class})
    String value;

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
