package example.domain.model.employee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 従業員登録
 */
public class EmployeeToRegister {

    @NotNull
    @Valid
    Name name;

    @NotNull
    @Valid
    MailAddress mailAddress;

    @NotNull
    @Valid
    PhoneNumber phoneNumber;

    public EmployeeToRegister(Name name, MailAddress mailAddress, PhoneNumber phoneNumber) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
    }

    public static EmployeeToRegister blank() {
        return new EmployeeToRegister(new Name(), new MailAddress(), new PhoneNumber());
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
