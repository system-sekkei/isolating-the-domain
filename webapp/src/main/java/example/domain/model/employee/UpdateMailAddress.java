package example.domain.model.employee;

/**
 * メールアドレスの変更
 */
public class UpdateMailAddress {

    EmployeeNumber employeeNumber;

    MailAddress mailAddress;

    public UpdateMailAddress(EmployeeNumber employeeNumber, MailAddress mailAddress) {
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
