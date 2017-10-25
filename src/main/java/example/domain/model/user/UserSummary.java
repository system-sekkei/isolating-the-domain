package example.domain.model.user;

public class UserSummary {
    UserIdentifier identifier;
    UserEmail email;
    Name name;

    public UserIdentifier identifier() {
        return identifier;
    }

    public UserEmail email() {
        return email;
    }

    public Name name() {
        return name;
    }
}
