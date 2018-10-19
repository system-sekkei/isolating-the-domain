package example.domain.model.user;

import static org.junit.jupiter.api.Assertions.*;

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
	void test_list() {
		UserSummary summary = sut.list().list().stream().filter(
				us -> "fukawa_teruyoshi@example.com".equals(us.identifier().toString())).findFirst().get();
		assertEquals(summary.mailAddress().toString(), "fukawa_teruyoshi_new@example.com");
	}
	
	@Test
	void test_findBy() {
		User user = sut.findBy(new UserIdentifier("fukawa_teruyoshi@example.com"));
		assertEquals(user.mailAddress().toString(), "fukawa_teruyoshi_new@example.com");
	}
}
