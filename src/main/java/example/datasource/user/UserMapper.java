package example.datasource.user;

import example.model.user.User;
import example.model.user.UserId;
import example.model.user.UserSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by haljik on 15/06/04.
 */
public interface UserMapper {

    User findBy(@Param("id") UserId id);

    List<UserSummary> list();

    void register(@Param("user") User user);
}
