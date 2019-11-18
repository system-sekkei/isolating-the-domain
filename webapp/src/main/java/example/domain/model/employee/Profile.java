package example.domain.model.employee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 従業員プロフィール
 */
public class Profile {

    @NotNull
    @Valid
    Name name;

    @NotNull
    @Valid
    MailAddress mailAddress;

    @NotNull
    @Valid
    PhoneNumber phoneNumber;

    public Profile(Name name, MailAddress mailAddress, PhoneNumber phoneNumber) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
    }

    public Name name() {
        return name;
    }

    public MailAddress mailAddress() {
        return mailAddress;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }
}
