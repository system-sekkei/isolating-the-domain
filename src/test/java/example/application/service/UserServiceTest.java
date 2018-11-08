package example.application.service;

import example.Application;
import example.domain.model.user.*;
import example.domain.type.age.DateOfBirth;
import example.domain.type.gender.Gender;
import example.domain.type.gender.GenderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class UserServiceTest {
    @Autowired
    UserService sut;

    @Test
    void list() {
        User user = sut.list().list().stream().filter(
                us -> us.identifier().value().equals(1L)).findFirst().get();
        assertAll(
                () -> assertEquals(user.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
                () -> assertEquals(user.phoneNumber().toString(), "03-1234-9999"),
                () -> assertEquals(user.dateOfBirth().toString(), "1988-02-29"),
                () -> assertEquals(user.gender().toString(), "不明"),
                () -> assertEquals(user.name().toString(), "布川 光義"));
    }

    @Test
    void findById() {
        User user = sut.findById(new UserIdentifier(1L));
        assertAll(
                () -> assertEquals(user.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
                () -> assertEquals(user.phoneNumber().toString(), "03-1234-9999"),
                () -> assertEquals(user.dateOfBirth().toString(), "1988-02-29"),
                () -> assertEquals(user.gender().toString(), "不明"),
                () -> assertEquals(user.name().toString(), "布川 光義"));
    }

    @Test
    void registerAndDelete() {
        Name name = new Name("Eiji Yamane");
        DateOfBirth dateOfBirth = new DateOfBirth(LocalDate.now());
        Gender gender = new Gender(GenderType.男性);
        PhoneNumber phoneNumber = new PhoneNumber("090-6559-1234");
        MailAddress mailAddress = new MailAddress("hogehoge_hogeo@example.com");
        UserCandidate user = new UserCandidate(name, mailAddress, phoneNumber);
        User registeredUser = sut.register(user);
        User foundUser = sut.findById(registeredUser.identifier());
        assertAll(
                () -> assertEquals(foundUser.name().toString(), user.name().toString()),
                () -> assertEquals(foundUser.phoneNumber().toString(), user.phoneNumber().toString()),
                () -> assertEquals(foundUser.dateOfBirth().toString(), user.dateOfBirth().toString()),
                () -> assertEquals(foundUser.gender().toString(), user.gender().toString()),
                () -> assertEquals(foundUser.mailAddress().toString(), user.mailAddress().toString())
        );
        sut.delete(foundUser);

        assertThrows(UserNotFoundException.class,
                () -> sut.findById((foundUser.identifier())));
    }
}
