package example.application.service;

import example.Application;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.employee.EmployeeRecordService;
import example.domain.model.employee.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class EmployeeRecordServiceTest {
    @Autowired
    EmployeeRecordService sut;
    @Autowired
    EmployeeQueryService query;

    @Test
    void list() {
        Employee employee = query.contractingEmployees().list().stream().filter(
                us -> us.employeeNumber().value().equals(1)).findFirst().get();
        assertAll(
                () -> assertEquals(employee.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
                () -> assertEquals(employee.phoneNumber().toString(), "03-1234-9999"),
                () -> assertEquals(employee.name().toString(), "布川 光義"));
    }

    @Test
    void findById() {
        Employee employee = query.choose(new EmployeeNumber(1));
        assertAll(
                () -> assertEquals(employee.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
                () -> assertEquals(employee.phoneNumber().toString(), "03-1234-9999"),
                () -> assertEquals(employee.name().toString(), "布川 光義"));
    }

    @Test
    void registerAndDelete() {
        Name name = new Name("Eiji Yamane");
        PhoneNumber phoneNumber = new PhoneNumber("090-6559-1234");
        MailAddress mailAddress = new MailAddress("hogehoge_hogeo@example.com");

        EmployeeNumber employeeNumber = sut.prepareNewContract();
        sut.registerName(employeeNumber, name);
        sut.registerPhoneNumber(employeeNumber, phoneNumber);
        sut.registerMailAddress(employeeNumber, mailAddress);
        sut.inspireContract(employeeNumber);

        Employee foundEmployee = query.choose(employeeNumber);
        assertAll(
                () -> assertEquals(foundEmployee.name().toString(), name.toString()),
                () -> assertEquals(foundEmployee.phoneNumber().toString(), phoneNumber.toString()),
                () -> assertEquals(foundEmployee.mailAddress().toString(), mailAddress.toString())
        );
        sut.expireContract(foundEmployee);
    }
}
