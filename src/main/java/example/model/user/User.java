package example.model.user;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Created by haljik on 15/06/04.
 */
public class User {
    @Valid
    UserId id;
    @Valid
    Name name;
    @Valid
    BirthDate birthDate;
    @Valid
    PhoneNumber phoneNumber;
    Password password;

    public UserId getId() {
        return this.id;
    }

    public Name getName() {
        return this.name;
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }
}
