package example.model.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by haljik on 15/06/04.
 */
public class UserId {
    String value;

    public UserId(String value) {
        this.value = value;
    }

    //For MyBatis
    UserId() {
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
