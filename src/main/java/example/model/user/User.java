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



    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(UserId id) {
        this.id = id;
    }



    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }
}
