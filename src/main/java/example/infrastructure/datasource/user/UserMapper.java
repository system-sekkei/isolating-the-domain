package example.infrastructure.datasource.user;

import example.domain.model.user.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User findBy(@Param("identifier") UserIdentifier id);

    List<User> list();

    void registerUser(@Param("userId") UserIdentifier userId);

    void deleteNameMapper(@Param("userId") UserIdentifier userId);

    void registerName(@Param("id") Long id, @Param("userId") UserIdentifier userId, @Param("name") Name name);

    void registerNameMapper(@Param("userId") UserIdentifier userId, @Param("nameId") Long nameId);

    void deletePhoneNumberMapper(@Param("userId") UserIdentifier userId);

    void registerPhoneNumber(@Param("id") Long id, @Param("userId") UserIdentifier userId, @Param("phoneNumber") PhoneNumber phoneNumber);

    void registerPhoneNumberMapper(@Param("userId") UserIdentifier userId, @Param("phoneNumberId") Long phoneNumberId);

    void deleteMailAddressMapper(@Param("userId") UserIdentifier userId);

    void registerMailAddress(@Param("id") Long id, @Param("userId") UserIdentifier userId, @Param("mailAddress") MailAddress mailAddress);

    void registerMailAddressMapper(@Param("userId") UserIdentifier userId, @Param("mailAddressId") Long mailAddressId);

    void delete(@Param("user") User user);

    long newUserIdentifier();

    long newUserNameIdentifier();

    long newUserPhoneNumberIdentifier();

    long newUserMailAddressIdentifier();
}
