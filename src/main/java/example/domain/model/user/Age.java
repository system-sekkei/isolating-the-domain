package example.domain.model.user;

/**
 * 年齢
 */
public class Age {
    int value;

    Age(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
