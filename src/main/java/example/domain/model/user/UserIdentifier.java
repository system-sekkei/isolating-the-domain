package example.domain.model.user;

/**
 * 利用者識別子
 */
public class UserIdentifier {

    Long value;

    public UserIdentifier() {
    }

    public UserIdentifier(String value) {
        this.value = Long.parseLong(value);
    }

    public UserIdentifier(Long value) {
        this.value = value;
    }

    public Long value() { return value; }
    @Override
    public String toString() {
        return value.toString();
    }
}
