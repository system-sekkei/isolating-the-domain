package example.domain.model.user;

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

    @NotNull(message = "性別を選択してください。")
    GenderType gender;

    @Valid
    PhoneNumber phoneNumber;

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

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name=" + name +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                '}';
    }
}
