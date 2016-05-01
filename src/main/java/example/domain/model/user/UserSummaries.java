package example.domain.model.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;

/**
 * Created by haljik on 15/06/04.
 */
public class UserSummaries {
    final List<UserSummary> values;

    public UserSummaries(List<UserSummary> values) {
        this.values = values;
    }

    public List<UserSummary> list() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
