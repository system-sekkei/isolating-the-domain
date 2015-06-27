package example.service.user

import example.TestConfiguration
import example.model.user.Password
import example.model.user.UserId
import example.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created by haljik on 15/06/04.
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = TestConfiguration.class)
@ActiveProfiles("test")
class UserServiceSpec extends Specification {

    @Autowired
    UserService service;

    @Autowired
    JdbcTemplate jdbcTemplate;

    def setup() {
        jdbcTemplate.execute("DELETE FROM USER.USERS")
        jdbcTemplate.execute(
                "INSERT INTO USER.USERS(USER_ID, NAME, PASSWORD) VALUES " +
                "('seiji.kawakami@sora-works.com', '河上 晴司','password1234')," +
                "('someone@ddd-alliance.org', 'DDD ALLIANCE','password5678')"
        )
    }


    def "ユーザがIDで取得できること"() {
        given:
        def id = new UserId('seiji.kawakami@sora-works.com')
        when:
        def user = service.findById(id)
        then:
        user.isPresent()
        def actual = user.get()
        actual.id.value == id.value
        actual.name.value == '河上 晴司'
        actual.hasSamePassword(new Password("password1234"))
    }

    def "全ユーザが取得できること"() {
        when:
        def users = service.list();
        then:
        users.list().size() == 2;
    }
}
