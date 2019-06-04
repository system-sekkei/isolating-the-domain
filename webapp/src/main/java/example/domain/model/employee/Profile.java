package example.domain.model.employee;

public class Profile {

    Name name;
    MailAddress mailAddress;
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
