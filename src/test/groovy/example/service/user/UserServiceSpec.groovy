package example.service.user

import example.TestConfiguration
import example.model.user.BirthDate
import example.model.user.Gender
import example.model.user.Name
import example.model.user.Password
import example.model.user.PhoneNumber
import example.model.user.User
import example.model.user.UserId
import example.service.UserService
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
                INSERT INTO USERS.USERS(USER_ID, NAME, PASSWORD) VALUES
                ('seiji.kawakami@sora-works.com', '河上 晴司','password1234'),
                ('someone@ddd-alliance.org', 'DDD ALLIANCE','password5678')
                """)
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

    def "ユーザーを登録できること"() {
        given:
        def user = new User()
        def id = new UserId("hogefuga@example.com")
        def name = new Name()
        def birthDate = new BirthDate()
        def phoneNumber = new PhoneNumber()
        def gender = new Gender()
        user.id = id
        name.value = "Hoge Fuga"
        user.name = name
        birthDate.year = 1989
        birthDate.month = 11
        birthDate.day = 21
        user.birthDate = birthDate
        phoneNumber.value = "0120-888-888"
        user.phoneNumber = phoneNumber
        gender.value ="1"
        user.gender = gender
        when:
        service.register(user)
        then:
        def actual = service.findById(id).get()
        actual.id.value == "hogefuga@example.com"
        actual.name.value == "Hoge Fuga"
        actual.birthDate.value.isEqual(LocalDate.of(1989, 11, 21)) == true
        actual.phoneNumber.value == "0120-888-888"
        actual.gender.value == "1"
    }

    def "ユーザーを更新できること"() {
        given:
        def user = new User()
        def id = new UserId("someone@ddd-alliance.org")
        def name = new Name()
        def birthDate = new BirthDate()
        def phoneNumber = new PhoneNumber()
        def gender = new Gender()
        user.id = id
        name.value = "Foo Bar"
        user.name = name
        birthDate.year = 2011
        birthDate.month = 8
        birthDate.day = 19
        user.birthDate = birthDate
        phoneNumber.value = "03-1234-5678"
        user.phoneNumber = phoneNumber
        gender.value ="2"
        user.gender = gender
        when:
        service.update(user)
        then:
        def actual = service.findById(id).get()
        actual.id.value == "someone@ddd-alliance.org"
        actual.name.value == "Foo Bar"
        actual.birthDate.value.isEqual(LocalDate.of(2011, 8, 19)) == true
        actual.phoneNumber.value == "03-1234-5678"
        actual.gender.value == "2"
    }

    def "ユーザが削除できること" () {
        given:
        def user = new User()
        user.id = new UserId("seiji.kawakami@sora-works.com")
        when:
        service.delete(user)
        def deleteUser = service.findById(user.id)
        then:
        !deleteUser.isPresent()
    }
}
