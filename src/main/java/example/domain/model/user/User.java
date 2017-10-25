package example.domain.model.user;

import javax.validation.Valid;

/**
 * Created by haljik on 15/06/04.
 */
public class User {

    @Valid
    UserIdentifier identifier;

    @Valid
    UserEmail email;

    @Valid
    Name name;

    @Valid
    DateOfBirth dateOfBirth;

    @Valid
    Gender gender;

    @Valid
    PhoneNumber phoneNumber;

    public User() {
        identifier = new UserIdentifier();
        email = new UserEmail();
        name = new Name();
        dateOfBirth = new DateOfBirth();
        gender = new Gender();
        phoneNumber = new PhoneNumber();
    }

    public UserIdentifier identifier() {
        return identifier;
    }

    public UserEmail email() {
        return email;
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

    @Override
    public String toString() {
        return "User{" +
                ", identifier=" + identifier +
                ", email=" + email +
                ", name=" + name +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                '}';
    }
}
