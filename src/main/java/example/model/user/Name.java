package example.model.user;

import example.model.user.validation.OnRegister;
import example.model.user.validation.OnUpdate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by haljik on 15/06/04.
 */
public class Name {
    @NotBlank(message = "名前を入力してください。", groups = {OnRegister.class, OnUpdate.class})
    String text;

    public Name(String text)  {
        this.text = text;
    }

    public Name() {
    }

    @Override
    public String toString() {
        return text;
    }
}
