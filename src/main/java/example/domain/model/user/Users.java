package example.domain.model.user;

import java.util.Collections;
import java.util.List;

/**
 * 利用者一覧
 */
public class Users {
    List<User> values;

    public Users(List<User> values) {
        this.values = values;
    }

    public List<User> list() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return "Users{" +
                "values=" + values +
                '}';
    }
}
