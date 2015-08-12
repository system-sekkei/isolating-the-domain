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
    Age age;
    @Valid
    PhoneNumber phoneNumber;
    Password password;

    public UserId getId() {
        return this.id;
    }

    public Name getName() {
        return this.name;
    }

    public Age getAge() {
        return age;
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

    public void setAge(Age age) {
        this.age = age;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean hasSamePassword(Password password) {
        return password.hasSameValue(password);
    }
}
