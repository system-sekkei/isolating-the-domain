package example.infrastructure.datasource.user;

import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.domain.model.user.UserSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User findBy(@Param("identifier") UserIdentifier id);

    List<UserSummary> list();

    void registerUser(@Param("user") User user);

    void registerName(@Param("id") Long id, @Param("user") User user);
    void registerNameMapper(@Param("userId") UserIdentifier userId, 
    		@Param("nameId") Long nameId);

    void registerPhoneNumber(@Param("id") Long id, @Param("user") User user);
    void registerPhoneNumberMapper(@Param("userId") UserIdentifier userId, 
    		@Param("phoneNumberId") Long phoneNumberId);

    void registerMailAddress(@Param("id") Long id, @Param("user") User user);
    void registerMailAddressMapper(@Param("userId") UserIdentifier userId, 
    		@Param("mailAddressId") Long mailAddressId);
    
    void registerDateOfBirth(@Param("id") Long id, @Param("user") User user);
    void registerDateOfBirthMapper(@Param("userId") UserIdentifier userId, 
    		@Param("dateOfBirthId") Long dateOfBirthId);

    void delete(@Param("user") User user);
}
