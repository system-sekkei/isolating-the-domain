package example.domain.model.user;

public class UserSummary {
    UserIdentifier identifier;
    Name name;
    DateOfBirth dateOfBirth;

    public UserIdentifier identifier() {
        return identifier;
    }

    public Name name() {
        return name;
    }

    public Age age() { return dateOfBirth.age(); }
}
