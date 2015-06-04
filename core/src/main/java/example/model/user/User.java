package example.model.user;

import java.util.Objects;

/**
 * Created by haljik on 15/06/04.
 */
public class User {
    UserId id;
    Name name;
    Password password;

    public UserId getId() {
        return this.id;
    }

    public Name getName() {
        return this.name;
    }

    public boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }
}
