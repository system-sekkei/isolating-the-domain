package example.domain.model.user;

import java.util.UUID;

import org.hibernate.validator.constraints.NotBlank;

public class UserIdentifier {

    @NotBlank(message = "IDを入力してください")
    String value = "";

    public UserIdentifier() {
    		this(UUID.randomUUID().toString());
    }

    public UserIdentifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
