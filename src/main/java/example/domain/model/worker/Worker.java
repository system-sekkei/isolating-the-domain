package example.domain.model.worker;

import javax.validation.Valid;

/**
 * 従業員
 */
public class Worker {
    @Valid
    WorkerNumber workerNumber;

    @Valid
    Name name;

    @Valid
    MailAddress mailAddress;

    @Valid
    PhoneNumber phoneNumber;

    @Deprecated
    public Worker() {
        workerNumber = new WorkerNumber();
        name = new Name();
        mailAddress = new MailAddress();
        phoneNumber = new PhoneNumber();
    }

    public WorkerNumber workerNumber() {
        return workerNumber;
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
                "workerNumber=" + workerNumber +
                ", name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
