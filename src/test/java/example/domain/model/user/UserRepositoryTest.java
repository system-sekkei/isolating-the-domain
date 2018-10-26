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
		try {
		UserSummary summary = sut.list().list().stream().filter(
				us -> "fukawa_teruyoshi@example.com".equals(us.identifier().toString())).findFirst().get();
		assertAll(
				() -> assertEquals(summary.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
				() -> assertEquals(summary.dateOfBirth.value, LocalDate.of(1988, 2, 29)),
				() -> assertEquals(summary.name().toString(), "布川 光義"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void findBy() {
		try {
		User user = sut.findBy(new UserIdentifier("fukawa_teruyoshi@example.com"));
		assertAll(
				() -> assertEquals(user.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
				() -> assertEquals(user.phoneNumber().toString(), "03-1234-9999"),
				() -> assertEquals(user.dateOfBirth().value, LocalDate.of(1988, 2, 29)),
				() -> assertEquals(user.gender().value, GenderType.不明),
				() -> assertEquals(user.name().toString(), "布川 光義"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void registerAndDelete() {
		try {
		UserCandidate user = sut.prototype();
		user.name.value = "Eiji Yamane";
		user.dateOfBirth.value = LocalDate.now();
		user.gender.value = GenderType.男性;
		user.phoneNumber.value = "090-6559-1234";
		user.mailAddress.value = "hogehoge_hogeo@example.com";
		User registeredUser = sut.register(user);
		User foundUser = sut.findBy(registeredUser.identifier);
		assertAll(
				() -> assertEquals(foundUser.name().toString(), user.name.value),
				() -> assertEquals(foundUser.phoneNumber().toString(), user.phoneNumber.value),
				() -> assertEquals(foundUser.dateOfBirth().value, user.dateOfBirth.value),
				() -> assertEquals(foundUser.gender().value, user.gender.value),
				() -> assertEquals(foundUser.mailAddress().toString(), user.mailAddress.value)
		);
		sut.delete(foundUser);
		assertNull(sut.findBy(foundUser.identifier));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
