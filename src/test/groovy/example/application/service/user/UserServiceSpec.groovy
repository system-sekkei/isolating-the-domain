package example.application.service.user

import example.TestConfiguration
import example.domain.model.user.*
import example.application.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDate

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
        jdbcTemplate.execute("DELETE FROM USERS.USERS")
        jdbcTemplate.execute("""
INSERT INTO users.users
(user_id, name, phone_number, date_of_birth, gender)
VALUES
 ('fukawa_teruyoshi@example.com', '布川 光良', '03-1234-5678','1988-04-08','男性'),
 ('kuriyama_yuino@example.com', '栗山 友以乃', '03-1234-5678','1988-04-08','女性'),
 ('fujimura_kaoru@example.com', '藤村 薫', '03-1234-5678','1988-04-08','男性'),
 ('ijuuin_ken@example.com', '伊集院 建', '03-1234-5678','1988-04-08','男性'),
 ('yamato_michiko@example.com', '大和 路子', '03-1234-5678','1988-04-08','女性'),
 ('miyake_yukiya@example.com', '三宅 有起子', '03-1234-5678','1988-04-08','女性');
                """)
    }


    def "ユーザがIDで取得できること"() {
        given:
        def id = new UserIdentifier('ijuuin_ken@example.com')
        when:
        def user = service.findById(id)
        then:
        user.identifier().toString() == id.toString()
        user.name().toString() == '伊集院 建'
    }

    def "全ユーザが取得できること"() {
        when:
        def users = service.list();
        then:
        users.list().size() == 6;
    }

    def "ユーザーを登録できること"() {
        given:
        def user = new User()
        user.identifier = new UserIdentifier("new_user@example.com")
        user.name = new Name("new_user")
        user.dateOfBirth = new DateOfBirth("1989/11/21")
        user.phoneNumber = new PhoneNumber("0120-888-888")
        user.gender = GenderType.男性
        when:
        service.register(user)
        then:
        User actual = service.findById(user.identifier())
        actual.identifier.toString() == "new_user@example.com"
        actual.name.toString() == "new_user"
        actual.dateOfBirth.date.isEqual(LocalDate.of(1989,11,21))
        actual.phoneNumber.toString() == "0120-888-888"
        actual.gender == GenderType.男性
    }

}
