package example.model.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by haljik on 15/06/04.
 */
public class Password {
    String value;

    public Password(String value) {
        this.value = value;
    }

    //For MyBatis
    Password() {
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean hasSameValue(Password password) {
        return value.equals(password.getValue());
    }
}
