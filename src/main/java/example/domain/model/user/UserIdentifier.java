package example.domain.model.user;

import java.util.UUID;

public class UserIdentifier {

    String value = "";

    public UserIdentifier() {
        this.value = UUID.randomUUID().toString();
    }

    public UserIdentifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
