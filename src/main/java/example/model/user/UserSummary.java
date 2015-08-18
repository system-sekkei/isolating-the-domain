package example.model.user;

public class UserSummary {
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
