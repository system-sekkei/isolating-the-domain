package example.domain.model.user;

import javax.validation.Valid;

/**
 * 利用者
 */
public class User {
    @Valid
    UserIdentifier identifier;

    @Valid
    Name name;

    @Valid
    MailAddress mailAddress;

    @Valid
    PhoneNumber phoneNumber;

    public User() {
        identifier = new UserIdentifier();
        name = new Name();
        mailAddress = new MailAddress();
        phoneNumber = new PhoneNumber();
    }

    public UserIdentifier identifier() {
        return identifier;
    }

    public Name name() {
        return name;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public MailAddress mailAddress() {
        return mailAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
