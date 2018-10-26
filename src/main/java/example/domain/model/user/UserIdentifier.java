package example.domain.model.user;

import javax.validation.constraints.NotNull;

public class UserIdentifier {

    @NotNull
    Long value = Long.valueOf(0L);

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
