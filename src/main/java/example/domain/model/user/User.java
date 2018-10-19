package example.domain.model.user;

import javax.validation.Valid;

/**
 * Created by haljik on 15/06/04.
 */
public class User {
    @Valid
    UserIdentifier identifier;

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

    public User() {
        identifier = new UserIdentifier();
        name = new Name();
        mailAddress = new MailAddress();
        dateOfBirth = new DateOfBirth();
        gender = new Gender();
        phoneNumber = new PhoneNumber();
    }

    public UserIdentifier identifier() {
        return identifier;
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
        return "User{" +
                "identifier=" + identifier +
                ", name=" + name +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
