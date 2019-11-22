package example.application.service;

import example.application.coordinator.employee.EmployeeRecordCoordinator;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.employee.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmployeeRecordServiceTest {
    @Autowired
    EmployeeRecordCoordinator sut;
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

        EmployeeNumber employeeNumber = sut.register(new EmployeeToRegister(name, mailAddress, phoneNumber));

        Employee foundEmployee = query.choose(employeeNumber);
        assertAll(
                () -> assertEquals(foundEmployee.name().toString(), name.toString()),
                () -> assertEquals(foundEmployee.phoneNumber().toString(), phoneNumber.toString()),
                () -> assertEquals(foundEmployee.mailAddress().toString(), mailAddress.toString())
        );
    }
}
