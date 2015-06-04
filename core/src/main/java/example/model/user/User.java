package example.model.user;

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

    boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }
}
