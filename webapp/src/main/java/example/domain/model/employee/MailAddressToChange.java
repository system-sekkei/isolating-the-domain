package example.domain.model.employee;

/**
 * メールアドレスの変更
 */
public class MailAddressToChange {

    EmployeeNumber employeeNumber;

    MailAddress mailAddress;

    public MailAddressToChange(EmployeeNumber employeeNumber, MailAddress mailAddress) {
        this.employeeNumber = employeeNumber;
        this.mailAddress = mailAddress;
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public MailAddress mailAddress() {
        return mailAddress;
    }
}
