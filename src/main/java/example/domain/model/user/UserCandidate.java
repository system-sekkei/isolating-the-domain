package example.domain.model.user;

import javax.validation.Valid;

/**
 * 利用者候補
 */
public class UserCandidate {
    @Valid
    Name name;

    @Valid
    MailAddress mailAddress;

    @Valid
    PhoneNumber phoneNumber;

    public UserCandidate() {
        this(new Name(), new MailAddress(), new PhoneNumber());
    }

    public UserCandidate(Name name, MailAddress mailAddress, PhoneNumber phoneNumber) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
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
        return "UserCandidate{" +
                "name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
