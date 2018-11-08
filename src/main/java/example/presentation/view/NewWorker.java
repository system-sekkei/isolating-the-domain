package example.presentation.view;

import example.domain.model.worker.MailAddress;
import example.domain.model.worker.Name;
import example.domain.model.worker.PhoneNumber;

import javax.validation.Valid;

public class NewWorker {
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
}
