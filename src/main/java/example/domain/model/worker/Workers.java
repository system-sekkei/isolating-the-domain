package example.domain.model.worker;

import java.util.Collections;
import java.util.List;

/**
 * 従業員一覧
 */
public class Workers {
    List<Worker> values;

    public Workers(List<Worker> values) {
        this.values = values;
    }

    public List<Worker> list() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return "Workers{" +
                "values=" + values +
                '}';
    }
}
