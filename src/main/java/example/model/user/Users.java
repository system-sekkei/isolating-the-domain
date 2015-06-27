package example.model.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;

/**
 * Created by haljik on 15/06/04.
 */
public class Users {
    final List<User> values;

    public Users(List<User> values) {
        this.values = values;
    }

    public List<User> list() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
