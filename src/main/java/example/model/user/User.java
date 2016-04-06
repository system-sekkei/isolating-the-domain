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
    UserId id;
    @Valid
    Name name;

    @Valid
    DateOfBirth dateOfBirth;
    @Valid
    PhoneNumber phoneNumber;
    @NotNull(message = "性別を選択してください。", groups = {OnRegister.class, OnUpdate.class})
    GenderType gender;

    Password password;

    public UserId getId() {
        return this.id;
    }


    public void setId(UserId id) {
        this.id = id;
    }


    public Name getName() {
        return name;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public GenderType getGender() {
        return gender;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                ", password=" + password +
                '}';
    }
}
