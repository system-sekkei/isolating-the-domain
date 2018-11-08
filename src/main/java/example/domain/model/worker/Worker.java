package example.domain.model.worker;

import javax.validation.Valid;

/**
 * 従業員
 */
public class Worker {
    @Valid
    WorkerIdentifier identifier;

    @Valid
    Name name;

    @Valid
    MailAddress mailAddress;

    @Valid
    PhoneNumber phoneNumber;

    public Worker() {
        identifier = new WorkerIdentifier();
        name = new Name();
        mailAddress = new MailAddress();
        phoneNumber = new PhoneNumber();
    }

    public WorkerIdentifier identifier() {
        return identifier;
    }

    public Name name() {
        return name;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public MailAddress mailAddress() {
        return mailAddress;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "identifier=" + identifier +
                ", name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
