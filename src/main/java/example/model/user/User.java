package example.model.user;

import example.model.user.validation.OnRegister;
import example.model.user.validation.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by haljik on 15/06/04.
 */
public class User {
    @Valid
    UserIdentifier identifier;
    @Valid
    Name name;

    @Valid
    DateOfBirth dateOfBirth;
    @Valid
    PhoneNumber phoneNumber;
    @NotNull(message = "性別を選択してください。", groups = {OnRegister.class, OnUpdate.class})
    GenderType gender;

    Password password;

    public UserIdentifier identifier() {return identifier;}
    public Name name() {
        return name;
    }

    public DateOfBirth dateOfBirth() {
        return dateOfBirth;
    }

    public GenderType gender() {
        return gender;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name=" + name +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                ", password=" + password +
                '}';
    }
}
