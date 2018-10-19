package example.domain.model.user;

import org.hibernate.validator.constraints.NotBlank;

public class UserIdentifier {

    @NotBlank(message = "IDを入力してください")
    String value = "";

    public UserIdentifier() {
    }

    public UserIdentifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
