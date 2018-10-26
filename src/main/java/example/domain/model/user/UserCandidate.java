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
    DateOfBirth dateOfBirth;

    @Valid
    Gender gender;

    @Valid
    PhoneNumber phoneNumber;

    public UserCandidate() {
        name = new Name();
        mailAddress = new MailAddress();
        dateOfBirth = new DateOfBirth();
        gender = new Gender();
        phoneNumber = new PhoneNumber();
    }

    public Name name() {
        return name;
    }

    public DateOfBirth dateOfBirth() {
        return dateOfBirth;
    }

    public Gender gender() {
        return gender;
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
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
