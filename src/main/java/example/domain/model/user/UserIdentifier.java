package example.domain.model.user;

import javax.validation.constraints.NotNull;

/**
 * 利用者識別子
 */
public class UserIdentifier {

    @NotNull
    Long value;

    public UserIdentifier() {
    }

    public UserIdentifier(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
