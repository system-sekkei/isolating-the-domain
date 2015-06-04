package example.datasource.user;

import example.model.user.User;
import example.model.user.UserId;
import org.apache.ibatis.annotations.Param;

/**
 * Created by haljik on 15/06/04.
 */
public interface UserMapper {

    User findBy(@Param("id") UserId id);
}
