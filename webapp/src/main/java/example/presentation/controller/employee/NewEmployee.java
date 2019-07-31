package example.presentation.controller.employee;

import example.domain.model.employee.MailAddress;
import example.domain.model.employee.Name;
import example.domain.model.employee.PhoneNumber;
import example.domain.model.employee.Profile;

import javax.validation.Valid;

public class NewEmployee {
    @Valid
    Name name;

    @Valid
    MailAddress mailAddress;

    @Valid
    PhoneNumber phoneNumber;

    public Name name() {
        return name;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public MailAddress mailAddress() {
        return mailAddress;
    }

    public Profile profile() {
        return new Profile(name, mailAddress, phoneNumber);
    }
}
