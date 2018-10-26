package example.domain.model.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import example.Application;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=Application.class)
class UserRepositoryTest {
	@Autowired
	UserRepository sut;
	
	@Test
	void list() {
		UserSummary summary = sut.list().list().stream().filter(
				us -> "fukawa_teruyoshi@example.com".equals(us.identifier().toString())).findFirst().get();
		assertAll(
				() -> assertEquals(summary.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
				() -> assertEquals(summary.dateOfBirth.value, LocalDate.of(1988, 2, 29)),
				() -> assertEquals(summary.name().toString(), "布川 光義"));
	}
	
	@Test
	void findBy() {
		try {
		User user = sut.findBy(new UserIdentifier("fukawa_teruyoshi@example.com"));
		assertAll(
				() -> assertEquals(user.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
				() -> assertEquals(user.phoneNumber().toString(), "03-1234-9999"),
				() -> assertEquals(user.dateOfBirth().value, LocalDate.of(1988, 2, 29)),
				() -> assertEquals(user.name().toString(), "布川 光義"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void register() {
		try {
		User user = sut.prototype();
		user.name.value = "Eiji Yamane";
		user.dateOfBirth.value = LocalDate.now();
		user.gender.value = GenderType.男性;
		user.phoneNumber.value = "090-6559-1234";
		user.mailAddress.value = "hogehoge_hogeo@example.com";
		sut.register(user);
		User registeredUser = sut.findBy(user.identifier);
		assertAll(
				() -> assertEquals(registeredUser.name().toString(), user.name.value),
				() -> assertEquals(registeredUser.phoneNumber().toString(), user.phoneNumber.value),
				() -> assertEquals(registeredUser.dateOfBirth().value, user.dateOfBirth.value),
				() -> assertEquals(registeredUser.mailAddress().toString(), user.mailAddress.value)
		);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
