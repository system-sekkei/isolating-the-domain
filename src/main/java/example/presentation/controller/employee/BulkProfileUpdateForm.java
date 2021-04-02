package example.presentation.controller.employee;

import example.domain.model.employee.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 従業員情報一括更新フォーム
 */
public class BulkProfileUpdateForm {

    @NotNull
    @Valid
    Name name;

    @NotNull
    @Valid
    MailAddress mailAddress;

    @NotNull
    @Valid
    PhoneNumber phoneNumber;

    public BulkProfileUpdateForm(Name name, MailAddress mailAddress, PhoneNumber phoneNumber) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
    }

    public static BulkProfileUpdateForm from(Employee employee) {
        return new BulkProfileUpdateForm(employee.name(), employee.mailAddress(), employee.phoneNumber());
    }

    public NameToChange updateName(EmployeeNumber employeeNumber) {
        return new NameToChange(employeeNumber, name);
    }

    public PhoneNumberToChange updatePhoneNumber(EmployeeNumber employeeNumber) {
        return new PhoneNumberToChange(employeeNumber, phoneNumber);
    }

    public MailAddressToChange updateMailAddress(EmployeeNumber employeeNumber) {
        return new MailAddressToChange(employeeNumber, mailAddress);
    }
}
