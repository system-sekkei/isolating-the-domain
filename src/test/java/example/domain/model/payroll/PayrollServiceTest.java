package example.domain.model.payroll;

import example.Application;
import example.domain.model.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class PayrollServiceTest {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PayrollRepository sut;

    @Test
    void hoge() {}
}
